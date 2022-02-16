#include <fcntl.h>
#include <stdio.h>
#include <sys/stat.h>
#include <unistd.h>
#include <string.h>
#include <stdlib.h>
#include <sys/wait.h>
#include "argus.h"


int pids[1024];
struct pid_list info[99999];


pid_list new_pid_list() {
    pid_list *l = malloc(sizeof(struct pid_list));
    l->command = "";
    l->status = -1;
    l->index = -1;
    return *l;
}


pid_list add_pid_list(char *string, int pid, int index) {
    struct pid_list info_pid;
    struct pid_list *res = &info_pid;

    info_pid.command = malloc(sizeof(char) * strlen(string));
    strcpy(info_pid.command, string);
    info_pid.pid = pid;
    info_pid.index = index;
    info_pid.status = -1;

    return *res;
}


args first_parse(char *string) {
    int i = 0;

    struct args out;
    struct args *res = &out;

    char *token = strtok(string, "|");

    while (token != NULL) {
        out.list[i] = token;
        token = strtok(NULL, "|");
        i++;
    }
    out.list[i] = NULL;
    return *res;
}


args second_parse(char *string) {
    int i = 0;

    struct args out;
    struct args *res = &out;

    char *token = strtok(string, " ");

    while (token != NULL) {
        out.list[i] = token;
        token = strtok(NULL, " ");
        i++;
    }
    out.list[i] = NULL;
    return *res;
}


args input_parser(char *string) {
    char *temp = malloc((sizeof(char) * strlen(string)));
    strcpy(temp, string);

    struct args out;
    struct args *res = &out;

    char *token = strtok(temp, " ");
    out.list[0] = token;

    token = strtok(NULL, "\n");
    out.list[1] = token;

    return *res;
}


int number_pipes(char *s, char c) {
    return *s == '\0' ? 0 : number_pipes(s + 1, c) + (*s == c);
}


void timeout_handler(int signum) {
    int client_to_server;
    char message[1024];

    client_to_server = open(FIFO_CLIENT_SERVER, O_WRONLY);

    if (signum == SIGALRM) {
        sprintf(message, "!! %d\n", getpid());
    } else if (signum == SIGUSR1) {
        sprintf(message, "() %d\n", getpid());
    } else if (signum == SIGINT) {
        sprintf(message, "() %d\n", getpid());
    }

    printf("Killing master child (%d) and its kids\n", getpid());
    for (int i = 0; i < 1024 && pids[i] != 0; i++) kill(pids[i], SIGKILL);

    write(client_to_server, message, sizeof(char) * strlen(message));
    kill(getpid(), SIGKILL);
    close(client_to_server);
}


void sig_handler(int signum) {
}


/*
void read_command(int command) {
    int i = 0, n = 0, cont = 0;
    char buf[1];
    char buf2[4];
    int reader = open("log.txt", O_RDONLY | O_APPEND | O_CREAT);
    int server_to_client = open(FIFO_SERVER_CLIENT, O_WRONLY);

    n = read(reader, buf, sizeof(buf)) > 0;
    while (n) {
        if (buf[0] == '~') {
            cont++;
            if (cont == 5) {
                i++;
            }
        } else cont = 0;
        if (i == command) {
            if (buf[0] != '~') {
                write(server_to_client, buf, 1);
            }
        }
        n = read(reader, buf, sizeof(buf)) > 0;
    }
    close(server_to_client);
    close(reader);
}
*/


void exec_commands(char string[], int inactive_time, int execute_time) {

    int numb_pipes = number_pipes(string, '|');
    int numb_commands = numb_pipes + 1;
    int fd[numb_pipes][2];
    int process_number;
    int status;
    int pid;
    int i;

    char *command = malloc(sizeof(char) * 1024);
    char message[1024];

    //int file = open("log.txt", O_WRONLY | O_APPEND | O_CREAT);
    int client_to_server = open(FIFO_CLIENT_SERVER, O_WRONLY);
    int server_to_client = open(FIFO_SERVER_CLIENT, O_WRONLY);

    for (process_number = 0; info[process_number].index != -1; process_number++);

    for (i = 0; i < 1024; i++) pids[i] = 0;

    strcpy(command, string);

    struct args out;
    out = first_parse(string);
    struct args a[50];
    i = 0;
    char *str = out.list[0];
    while (i < numb_commands) {
        a[i] = second_parse(str);
        i++;
        str = out.list[i];
    }

    printf("\n|  Process %d  |\n\n", process_number);

    pipe(fd[0]);

    signal(SIGALRM, timeout_handler);
    alarm(execute_time);


    for (i = 0; i <= numb_pipes; i++) {
        pipe(fd[i]);

        if ((pid = fork()) == 0) {

            alarm(inactive_time);
            printf("%d fork\n", i);
            close(fd[i][0]);

            if (i > 0) {
                dup2(fd[i - 1][0], 0);
                close(fd[i - 1][0]);
            }

            if (i == numb_pipes) {
                //dup2(file, 1);
                dup2(server_to_client, 1);
            } else {
                dup2(fd[i][1], 1);
            }

            close(fd[i][1]);
            info[process_number].status++;
            execvp(a[i].list[0], a[i].list);
            exit(-1);
        }

        close(fd[i - 1][0]);
        close(fd[i][1]);

        pids[i] = pid;
    }

    close(fd[i - 1][0]);


    for (int j = 0; j < numb_commands; j++) {
        waitpid(pids[i], &status, 0);
        printf("%d\n" ,(WIFEXITED(status)));
        if (!WIFEXITED(status)) {
            printf("Exit Status : %d\n", WEXITSTATUS(status));
            info[process_number].status = 1;

            sprintf(message, "££ %d\n", getpid());
            write(client_to_server, message, sizeof(char) * strlen(message));
            close(client_to_server);
            return;
        }
    }

    //write(file, "~~~~~\n", 6);
    //read_command(0);
    //close(file);

    info[process_number].status = 0;

    sprintf(message, "$$ %d\n", getpid());
    write(client_to_server, message, sizeof(char) * strlen(message));
    close(client_to_server);
}


int main() {

    int execute_time = DEFAULT_EXECUTE_TIME;
    int inactive_time = DEFAULT_INACTIVE_TIME;

    int global_count = 0;

    for (int i = 0; i < 99999; i++) {
        info[i] = new_pid_list();
    }

    char buf[BUFFER];

    mkfifo(FIFO_CLIENT_SERVER, 0666);
    mkfifo(FIFO_SERVER_CLIENT, 0666);

    int client_to_server = open(FIFO_CLIENT_SERVER, O_RDONLY);
    int server_to_client = open(FIFO_SERVER_CLIENT, O_WRONLY);

    write(1, MSG_SERVER_ON, sizeof(char) * strlen(MSG_SERVER_ON));


    while (1) {

        signal(SIGPIPE, sig_handler);

        memset(buf, 0, sizeof(buf));

        read(client_to_server, buf, sizeof(char) * BUFFER);

        if (strcmp(buf, "") == 0) {
            continue;
        }

        struct args input = input_parser(buf);

        printf("Input : %s\n", buf);
        printf("Command : %s\n", input.list[1]);


        if (strcmp("exit", buf) == 0 || strcmp("exit\n", buf) == 0) {
            printf("Server OFF.\n");
            break;


        } else if (strcmp("-h\n", buf) == 0 || strcmp("-h", buf) == 0) {
            write(server_to_client, MSG_SERVER_HELP, sizeof(char) * strlen(MSG_SERVER_HELP));


        } else if (strcmp("-t", input.list[0]) == 0) {

            int process = atoi(input.list[1]);
            if (process < global_count) {
                int pid = info[process].pid;
                kill(pid, SIGUSR1);
                write(server_to_client, MSG_PROCESS_TERMINATED, sizeof(char) * strlen(MSG_PROCESS_TERMINATED));
            } else {
                write(server_to_client, MSG_NO_SUCH_PROCESS, sizeof(char) * strlen(MSG_NO_SUCH_PROCESS));
            }


        } else if (strcmp("-t", buf) == 0 || strcmp("-t\n", buf) == 0) {
            write(server_to_client, MSG_T_HELP, sizeof(char) * strlen(MSG_T_HELP));


        } else if (strcmp("-i", input.list[0]) == 0) {
            int time = atoi(input.list[1]);
            inactive_time = time;
            write(server_to_client, MSG_I, sizeof(char) * strlen(MSG_I));


        } else if (strcmp("-i", buf) == 0 || strcmp("-i\n", buf) == 0) {
            write(server_to_client, MSG_I_HELP, sizeof(char) * strlen(MSG_I_HELP));


        } else if (strcmp("-m", input.list[0]) == 0) {
            int time = atoi(input.list[1]);
            execute_time = time;
            write(server_to_client, MSG_M, sizeof(char) * strlen(MSG_M));


        } else if (strcmp("-m", buf) == 0 || strcmp("-m\n", buf) == 0) {
            write(server_to_client, MSG_M_HELP, sizeof(char) * strlen(MSG_M_HELP));


        } else if (strcmp("-l", buf) == 0 || strcmp("-l\n", buf) == 0) {

            for (int i = 0; info[i].index != -1; i++) {
                if (info[i].status < 0) {
                    char snum[1024];
                    sprintf(snum, "#%d: %s\n", i, info[i].command);
                    write(server_to_client, snum, sizeof(char) * strlen(snum));
                }
            }


        } else if (strcmp("-r\n", buf) == 0 || strcmp("-r", buf) == 0) {
            char *state = malloc(sizeof(char) * 30);

            for (int i = 0; info[i].index != -1; i++) {
                if (info[i].status >= 0) {

                    if (info[i].status == 0) {
                        strcpy(state, MSG_CONC);
                    }

                    if (info[i].status == 1) {
                        strcpy(state, MSG_MAX_INAT_TIME);
                    }

                    if (info[i].status == 2) {
                        strcpy(state, MSG_MAX_EXEC_TIME);
                    }

                    if (info[i].status == 3) {
                        strcpy(state, MSG_TERM);
                    }
                    char snum[1024];
                    sprintf(snum, "#%d,%s %s\n", i, state, info[i].command);
                    write(server_to_client, snum, sizeof(char) * strlen(snum));
                }
            }


        } else if (strcmp("!!", input.list[0]) == 0) {
            int i = 0;
            int pid = atoi(input.list[1]);

            while (info[i].pid != pid) {
                i++;
            }
            char *aux = malloc(sizeof(char) * 10);
            info[i].status = 2;
            sprintf(aux, "pid %d, status %d, command %s\n", info[i].pid, info[i].status, info[i].command);
            write(server_to_client, aux, sizeof(char) * strlen(aux));


        } else if (strcmp("££", input.list[0]) == 0) {
            int i = 0;
            int pid = atoi(input.list[1]);
            while (info[i].pid != pid) {
                i++;
            }
            char *aux = malloc(sizeof(char) * 10);
            info[i].status = 1;
            sprintf(aux, "pid %d, status %d, command %s\n", info[i].pid, info[i].status, info[i].command);
            write(server_to_client, aux, sizeof(char) * strlen(aux));


        } else if (strcmp("$$", input.list[0]) == 0) {
            int i = 0;
            int pid = atoi(input.list[1]);
            while (info[i].pid != pid) {
                i++;
            }
            char *aux = malloc(sizeof(char) * 10);
            info[i].status = 0;
            sprintf(aux, "pid %d, status %d, command %s\n", info[i].pid, info[i].status, info[i].command);
            write(server_to_client, aux, sizeof(char) * strlen(aux));


        } else if (strcmp("()", input.list[0]) == 0) {
            int i = 0;
            int pid = atoi(input.list[1]);
            while (info[i].pid != pid) {
                i++;
            }
            char *aux = malloc(sizeof(char) * 10);
            info[i].status = 3;
            sprintf(aux, "pid %d, status %d, command %s\n", info[i].pid, info[i].status, info[i].command);
            write(server_to_client, aux, sizeof(char) * strlen(aux));


        } else if (strcmp("-e", input.list[0]) == 0) {
            int pid;
            if ((pid = fork()) == 0) {
                signal(SIGUSR1, timeout_handler);
                exec_commands(input.list[1], inactive_time, execute_time);
                return 0;
            }
            info[global_count] = add_pid_list(input.list[1], pid, global_count);
            global_count++;


        } else if (strcmp("-e", buf) == 0 || strcmp("-e\n", buf) == 0) {
            write(server_to_client, MSG_E_HELP, sizeof(char) * strlen(MSG_E_HELP));


        } else if (strcmp("-o", buf) == 0 || strcmp("-o\n", buf) == 0) {
            write(server_to_client, MSG_O, sizeof(char) * strlen(MSG_O));


        } else {
            write(server_to_client, MSG_NO_SUCH_PROCESS, sizeof(char) * strlen(MSG_NO_SUCH_PROCESS));
        }

    }

    close(client_to_server);
    close(server_to_client);

    unlink(FIFO_CLIENT_SERVER);
    unlink(FIFO_SERVER_CLIENT);
    return 0;
}