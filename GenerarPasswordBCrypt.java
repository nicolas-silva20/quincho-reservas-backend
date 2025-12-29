import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class GenerarPasswordBCrypt {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        
        // Generar hash para "elumbral2024"
        String hash1 = encoder.encode("elumbral2024");
        System.out.println("Hash para 'elumbral2024':");
        System.out.println(hash1);
        System.out.println();
        
        // Generar hash para "umbral123"
        String hash2 = encoder.encode("umbral123");
        System.out.println("Hash para 'umbral123':");
        System.out.println(hash2);
    }
}
