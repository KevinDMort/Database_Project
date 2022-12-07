import java.sql.Array;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void printEnStuderende(Studerende s)
    {
        System.out.printf("Studerende nr %d: %s %s,Klasse: %s Adresse: %s %s, TLF: %s ",s.getStdnr(),s.fnavn,s.enavn,s.klasse,s.adresse,s.postnr,s.mobil);
        if(s.getFagListe().isEmpty()==false)
        {
            System.out.print("Fag: ");
                for(int i =0; i< s.getFagListe().size();i++)
                {
                System.out.print(s.getFagListe().get(i).fagnavn+ ",");
                }
        }
        System.out.println();
    }

    public static void printAlleStuderende(ArrayList<Studerende> s)
    {
        for (int i=0; i<s.size(); i++)
        {
            printEnStuderende(s.get(i));
            System.out.println();
        }
    }
    public static void menu()
    {
        DBSql db = new DBSql();
        int valg;
        String fagNavn;
        int stdnr;
        int fagnr;
        String nyKlasse;
        ArrayList<Studerende> list = new ArrayList();
        Scanner input = new Scanner(System.in);
        System.out.println("1. Opret fag");
        System.out.println("2. Opret sturedende");
        System.out.println("3. Tildel en studerende et nyt fag");
        System.out.println("4. Udskriv stamdata om en studerende");
        System.out.println("5. Udskriver alle studerende");
        System.out.println("6. Udskriv alle oplysninger om en studerende");
        System.out.println("7. Vis alle studerende der er tilmeldt et fag");
        System.out.println("8. Opdaterer en studerendes klasse");
        System.out.println("9. Slet en studerende");
        System.out.println("0. Slukker programmet");
        System.out.print("Indtast dit valg:");
        valg = input.nextInt();
        switch(valg)
        {
            case 1:
                System.out.println("Indtast fagnavn:");
                Fag f1 = new Fag(1,input.next());
                menu();
                break;
            case 2:
                System.out.println("Indtast Oplysninger på en ny studerende: Fornavn, Efternavn,Adresse, postnr,mobile,klasse");
                Studerende s = new Studerende(input.next(),input.next(),input.next(),input.next(),input.next(),input.next());
                db.indsaetStud(s);
                menu();
                break;
            case 3:
                System.out.println("Indtast studienummer");
                stdnr = input.nextInt();
                System.out.println("Indtast fagnr");
                fagnr= input.nextInt();
                db.tilmeldFag(stdnr,fagnr);
                menu();
                break;
            case 4:
                System.out.println("Indtast studienummer");
                stdnr = input.nextInt();
                Studerende s1 = db.soegStdnr(stdnr);
                printEnStuderende(s1);
                menu();
                break;
            case 5:
                list =db.alleoplysninger();
                printAlleStuderende(list);
                menu();
                break;
            case 6:
                System.out.println("Indtast studienummer");
                stdnr = input.nextInt();
                Studerende s2 = db.soegAltStdnr(stdnr);
                printEnStuderende(s2);
                menu();
                break;
            case 7:
                System.out.println("Indtast fagnr");
                list = db.allestudmedfag(input.nextInt());
                printAlleStuderende(list);
                menu();
                break;
            case 8:
                System.out.println("Indtast studienummer");
                stdnr = input.nextInt();
                System.out.println("Indtast ny Klasse");
                nyKlasse =input.next();
                db.updateStudKlasse(stdnr,nyKlasse);
                menu();
                break;
            case 9:
                System.out.println("Indtast studienummer");
                db.sletStuderende(input.nextInt());
                menu();
                break;
            case 0:
                break;
        }
    }

    public static void main(String[] args) {

       menu();
    }
}
