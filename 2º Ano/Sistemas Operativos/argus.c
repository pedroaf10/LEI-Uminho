#include <stdio.h>
#include <fcntl.h>
#include <unistd.h>
#include <string.h>
#include "argus.h"


int main(int argc, char const *argv[]) {
    int i;
    char buffer[BUFFER];
    char str[BUFFER];
    char strf[BUFFER];

    int client_to_server = open(FIFO_CLIENT_SERVER, O_WRONLY);
    int server_to_client = open(FIFO_SERVER_CLIENT, O_RDONLY);

    if (argc > 1) {

        memset(str, 0, sizeof(str));

        for (i = 1; i < argc; i++) {
            strcat(str, argv[i]);
            if (i != argc - 1) {
                strcat(str, " ");
            } else {
                strcat(str, "\n");
            }
        }


        write(client_to_server, str, BUFFER * sizeof(char));

        int n = read(server_to_client, buffer, BUFFER * sizeof(char));
        write(1, buffer, n);
        perror("Write:");
        memset(str, 0, sizeof(str));
    }
    else{
        memset(strf, 0, sizeof(str));
        printf("Input message to server: ");
        printf("%s", strf);
        int num = read(0, strf, BUFFER);


        write(client_to_server, strf, num);
        perror("Write:");


        int n = read(server_to_client, buffer, BUFFER);
        write(1, buffer, n);
        memset(buffer, 0, sizeof(buffer));
    }

    close(client_to_server);
    close(server_to_client);

    return 0;
}