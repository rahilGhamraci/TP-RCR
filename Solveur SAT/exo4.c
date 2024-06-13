#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define MAX_LINE_LENGTH 1000

int main() {
    // Ouvrir le fichier de la base de connaissances en mode lecture
    FILE *file = fopen("test.cnf", "r");
    if (file == NULL) {
        fprintf(stderr, "Erreur lors de l'ouverture du fichier.\n");
        return 1;
    }

    // Lire le contenu du fichier
    char content[MAX_LINE_LENGTH * 100]; // 100 lignes max de 1000 caractères chacune
    content[0] = '\0'; // Assure que la chaîne commence par une chaîne vide
    char line[MAX_LINE_LENGTH];

   char firstLine[MAX_LINE_LENGTH];
    fgets(firstLine, MAX_LINE_LENGTH, file);
    
    while (fgets(line, MAX_LINE_LENGTH, file) != NULL) {
        strcat(content, line);
    }

    // Fermer le fichier en mode lecture
    fclose(file);

    // Ouvrir le fichier de la base de connaissances en mode écriture
    file = fopen("test.cnf", "w");

 

    if (file == NULL) {
        fprintf(stderr, "Erreur lors de l'ouverture du fichier.\n");
        return 1;
    }

    // Modifier la première ligne
    fprintf(file, "p cnf 5 10\n"); // Nouvelle première ligne avec le nombre de clauses modifié

    // Copier le contenu lu du fichier
    fprintf(file, "%s", content);

    // Ajouter une nouvelle formule à la base de connaissances
    int newA = -1;
    int newB = -2;
    fprintf(file, "\n%d %d 0\n", newA, newB);

    // Fermer le fichier
    fclose(file);

    // Appeler le solveur SAT avec la base de connaissances résultante
    system("ubcsat -alg saps -i test.cnf -solve");

    return 0;
}
