//
// Created by pedro on 14/06/20.
//

#ifndef TRABALHO_SO_ARGUS_H
#define TRABALHO_SO_ARGUS_H


#define BUFFER 999999
#define DEFAULT_EXECUTE_TIME 10
#define DEFAULT_INACTIVE_TIME 5

#define MSG_SERVER_HELP "-i n inactive time secs\n-m n running time secs\n-e p1 | p2 ...| pn run p1 | p2 ... | pn\n-l currently running taks\n-t n finish n process\n-r history of tasks \n-h help\n"
#define MSG_SERVER_ON "Server ON.\n"
#define MSG_PROCESS_TERMINATED "The process was terminated\n"
#define MSG_NO_SUCH_PROCESS "No such process\n"
#define MSG_T_HELP "To kill a process insert -t number_process\n"
#define MSG_I "The maximum time was changed\n"
#define MSG_I_HELP "To define the maximum time without communication insert -i max time\n"
#define MSG_M "The maximum time was changed\n"
#define MSG_M_HELP "To define the maximum time insert -m maxtime\n"
#define MSG_E_HELP "Execute task -e p1 | p2 ...| pn to execute p1 | p2 ... | pn, or ./argus ''p1 | p2 ... | pn'' \n"
#define MSG_O "Unfortunately not working properly\n"
#define MSG_MAX_INAT_TIME " max inactividade:"
#define MSG_CONC " concluida:"
#define MSG_MAX_EXEC_TIME " max execucao:"
#define MSG_TERM " terminada:"

static char *const FIFO_CLIENT_SERVER = "/tmp/client_to_server_fifo";

static char *const FIFO_SERVER_CLIENT = "/tmp/server_to_client_fifo";

typedef struct args {
    char *list[50];
} args;

typedef struct pid_list {
    char *command;
    int pid;
    int status;
    int index;
} pid_list;

/**
 * Cria uma nova pid_list.
 * @return A pid_list criada.
 */
pid_list new_pid_list();

/**
 * Adiciona uma nova tarefa a estrutura.
 * @param string Comando inserido.
 * @param pid Numero do pid.
 * @param index Numero de tarefas.
 * @return A estrutura atualizada.
 */
pid_list add_pid_list(char *string, int pid, int index);

/**
 * Diz quantos pipes existem numa string.
 * @param s A string para analisar.
 * @param c O caracter '|'.
 * @return Quantidade de pipes.
 */
int number_pipes(char *s, char c);

/**
 * Handler para sinais recebidos pelo filho master.
 * Lida com o sinal SIGALRM para detetar tempo maximo de execucao.
 * Lida com o sinal SIGUSR1 para detetar se foi termindado por -t.
 * @param signum Sinal envidado ao handler.
 */
void timeout_handler(int signum);

/**
 * Handler de sinais.
 * @param signum  Sinal recebido.
 */
void sig_handler(int signum);

/**
 * Read command from file.
 * @param command Command to read.
 */
void read_command(int command);

/**
 * Executa uma Tarefa.
 * @param string String contendo a tarefa.
 * @param inactive_time Tempo de inatividade maximo.
 * @param execute_time  Tempo de execucao maximo.
 */
void exec_commands(char *string, int inactive_time, int execute_time);
#endif
