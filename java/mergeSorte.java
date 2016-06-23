package br.com.aula;


import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Main {
    /**
     * @filename variável definida para caminho do arquivoa ser lido, ex.: lista.txt
     **/
    static final String FILENAME = "/home/israeleriston/Documentos/projetos/aula/src/br/com/aula/lista.txt";
    /**
     * @listaordenada arquivo a ser gerado contendo a lista ordenada
     **/
    static final String LISTAORDENADA = "listaordenada.txt";
    /**
     * @count variável utilizada para contar a quantidade de itens do arquivo lista.txt
     */
    static int count = 0;
    /**
     * @result variável utilizada armazenar a quantidade de linhas lidas do arquivo
     */
    static List<String> result = new ArrayList<>();

    static Boolean isEmpty = Boolean.FALSE;

    static String[] resultado;

    public static void main(String... args) throws IOException {

        readFile();
        ordering();
        logger();
        createFile();

    }

    private static void logger() {
        if (!isEmpty) {
            result.forEach(lineOrdering -> System.out.println(" lista ordenada: " + lineOrdering));
            System.out.println(" total de registros: " + count);
        }
    }

    private static void readFile() throws IOException {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(FILENAME), StandardCharsets.UTF_8)) {

            for (; ; ) {

                String line = reader.readLine();
                if (line == null)
                    break;

                result.add(line);
                System.out.println(" lista desordenada: " + line);
                count++;
            }

            if (result.isEmpty()) {
                System.out.println("O arquivo lido está vazio.");
                isEmpty = Boolean.TRUE;
                return;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(" total de registros lidos: " + count);
    }


    private static void ordering() {
        String[] resultCopy = result.toArray(new String[result.size()]);
        mergeSort(resultCopy);
        result = new ArrayList<>();
        for (String ordenado : resultado) {
            result.add(ordenado);
        }

    }


    private static void createFile() throws IOException {

        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(Paths.get(LISTAORDENADA), StandardCharsets.UTF_8)) {

            result.forEach(line -> {
                try {
                    bufferedWriter.write(line);
                    bufferedWriter.newLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            bufferedWriter.write(" TOTAL DE REGISTROS DO ARQUIVO: " + count);
            bufferedWriter.flush();
            bufferedWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void mergeSort( String [ ] a ) {
        System.out.println(" iniciando o merge sorte... ");
        System.out.println(" tamanho do array " + a.length);
        String[ ] tmpArray = new String[ a.length ];
        mergeSort( a, tmpArray, 0, a.length - 1 );
    }

    /**
     * Método interno que faz chamadas recursivas
     */
    private static void mergeSort( String [ ] a, String [ ] tmpArray,
                                   int left, int right ) {

        if( left < right ) {
            System.out.println(" dividindo...");
            int center = ( left + right ) / 2;
            mergeSort( a, tmpArray, left, center );
            mergeSort( a, tmpArray, center + 1, right );
            merge( a, tmpArray, left, center + 1, right );
        }
    }

    /**
     * Metódo interno que ordena duas partes de um subarray.
     */
    private static void merge(String [ ] a, String [ ] tmpArray,
                                  int leftPos, int rightPos, int rightEnd ) {
        System.out.println(" conquistando...");
        int leftEnd = rightPos - 1;
        int tmpPos = leftPos;
        int numElements = rightEnd - leftPos + 1;

        // loop principal
        while( leftPos <= leftEnd && rightPos <= rightEnd )
            if( ((Comparable<String>)a[ leftPos ]).compareTo( a[ rightPos ] ) <= 0 )
                tmpArray[ tmpPos++ ] = a[ leftPos++ ];
            else
                tmpArray[ tmpPos++ ] = a[ rightPos++ ];

        while( leftPos <= leftEnd )
            tmpArray[ tmpPos++ ] = a[ leftPos++ ];

        while( rightPos <= rightEnd )
            tmpArray[ tmpPos++ ] = a[ rightPos++ ];

        // Copiando o array temporário de volta
        for( int i = 0; i < numElements; i++, rightEnd-- )
            a[rightEnd] = tmpArray[rightEnd];

        resultado = a;
    }

}

