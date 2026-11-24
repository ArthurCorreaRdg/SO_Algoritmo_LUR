
import java.util.LinkedHashMap;
import java.util.Scanner;

public class GerenciadorMemoriaLRU {

    private final int capacidadeMemoria;
    private final LinkedHashMap<String, Boolean> memoria;

    private int acertosMemoria = 0;
    private int faltasMemoria = 0;

    public GerenciadorMemoriaLRU(int capacidadeMemoria) {
        if (capacidadeMemoria <= 0) {
            throw new IllegalArgumentException("A capacidade da memória deve ser maior que zero.");
        }

        this.capacidadeMemoria = capacidadeMemoria;
        this.memoria = new LinkedHashMap<>(capacidadeMemoria, 0.75f, true);
    }

    public void acessarPagina(String pagina) {
        System.out.println("\nAcessando página: " + pagina);

        if (memoria.containsKey(pagina)) {
            registrarAcerto(pagina);
        } else {
            registrarFalta(pagina);
        }
        exibirEstadoMemoria();
    }

    private void registrarAcerto(String pagina) {
        acertosMemoria++;

        memoria.get(pagina);

        System.out.println("Resultado: ACERTO DE MEMÓRIA");
        System.out.println("A página já estava armazenada.");
    }

    private void registrarFalta(String pagina) {
        faltasMemoria++;

        System.out.println("Resultado: FALTA DE PÁGINA");

        if (memoria.size() >= capacidadeMemoria) {

            removerPaginaMenosUtilizada();
        }

        memoria.put(pagina, true);
        System.out.println("Página " + pagina + " carregada na memória.");
    }

    private void removerPaginaMenosUtilizada() {

        String paginaRemovida = memoria.keySet().iterator().next();

        memoria.remove(paginaRemovida);

        System.out.println("Página removida pelo algoritmo LRU: " + paginaRemovida);
    }

    private void exibirEstadoMemoria() {

        System.out.println("Estado atual da memória: " + memoria.keySet());

        System.out.println("Acertos de memória: " + acertosMemoria);
        System.out.println("Faltas de página: " + faltasMemoria);

        System.out.println("------------------------------------");
    }

    public void exibirResultados() {

        int totalAcessos = acertosMemoria + faltasMemoria;

        System.out.println("\n========== RESULTADO FINAL ==========");
        System.out.println("Total de acessos: " + totalAcessos);
        System.out.println("Acertos de memória: " + acertosMemoria);
        System.out.println("Faltas de página: " + faltasMemoria);
        System.out.println("=====================================");
    }

    public static void main(String[] args) {

        Scanner leitor = new Scanner(System.in);

        System.out.println("=== SIMULADOR DE GERENCIAMENTO DE MEMÓRIA ===");

        System.out.print("Informe a capacidade da memória: ");
        int capacidade = leitor.nextInt();

        leitor.nextLine();

        GerenciadorMemoriaLRU gerenciador = new GerenciadorMemoriaLRU(capacidade);

        System.out.print("Informe as páginas separadas por espaço: ");
        String entrada = leitor.nextLine().trim();

        if (entrada.isEmpty()) {
            System.out.println("Nenhuma página foi informada.");
        } else {
            String[] paginas = entrada.split("\\s+");

            for (String pagina : paginas) {
                gerenciador.acessarPagina(pagina);
            }
            gerenciador.exibirResultados();
        }
        leitor.close();
    }
}
