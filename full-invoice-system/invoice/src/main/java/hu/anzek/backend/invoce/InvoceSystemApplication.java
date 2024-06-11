package hu.anzek.backend.invoce;

import hu.anzek.backend.invoce.datalayer.init.InitDbase;
import hu.anzek.backend.invoce.datalayer.model.InvUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "hu.anzek.backend")
public class InvoceSystemApplication implements CommandLineRunner{

    // deklarálunk egy felhasználót 
    // amely majd egy InvUser belépésével ki lesz töltve a "SystemLoginController" -ben
    // viszont így mindenhonnan hivatkozhatunk rá...
    public static InvUser aktivUser = new InvUser();
    
    @Autowired
    private InitDbase initDbase;
   
    public static void main(String[] args) {
            SpringApplication.run(InvoceSystemApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception{

        System.out.println("\nHello Vilag(!)\nEz itt a 176-os backend csoport iskolapeldaja az InvoiceSystem - projekt!\n");
        this.initDbase.initDbase();
    }
}
