import java.util.Scanner;
class Model {
  private static final char[][] start = {
    {'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S'},
    {'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S'},
    {'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S'},
    {'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S'},
    {'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S'},
    {'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S'},
    {'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S'},
    {'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S'},
    {'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'I', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S'},
    {'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S'},
    {'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S'},
    {'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S'},
    {'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S'},
    {'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S'},
    {'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S'},
    {'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S'},
    {'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S'}
  };
  private char[][] people = start.clone();
  private int susceptible = 288,
              infected = 1,
              recovered = 0,
              dead = 0,
              critical = 0,
              vaccinated = 0,
              days = 0,
              vD;
  private double tP,
                vP,
                hC,
                rP,
                rPINB,
                sIP,
                dPIC,
                rPIC;
  
  
  public Model(double a, int b, double c, double d, double e, double f, double g, double h, double i) {
    tP = a; // Transmission probability
    vD = b; // Vaccination starts after how many days
    vP = c; // Probability of vaccination
    hC = d; // Number of cases before high critical rate
    rP = e; // Recovery probability
    rPINB = f; // Recovery probability if not bad
    sIP = g; // Stay infected probability
    dPIC = h; // Death rate if critical
    rPIC = i; // Recovery rate if critical
  }
  public void reset() {
    people = start.clone();
    susceptible = 288;
    infected = 1;
    recovered = 0;
    dead = 0;
    critical = 0;
    vaccinated = 0;
    days = 0;
  }
  public void step() {
    int infected1 = 0;
    char[][] people0 = new char[17][17];
    for(int i = 0; i < 17; i++) {
      people0[i] = people[i].clone();
    }
    for(int i = 0; i < 17; i++) {
      for(int j = 0; j < 17; j++) {
        if(people0[i][j] == 'S') {
          if(i < 16) {
            if(people0[i + 1][j] == 'I' || people0[i + 1][j] == 'C') {
              infected1++;
            }
          }
          if(i > 0) {
            if(people0[i - 1][j] == 'I' || people0[i - 1][j] == 'C') {
              infected1++;
            }
          }
          if(j > 0) {
            if(people0[i][j - 1] == 'I' || people0[i][j - 1] == 'C') {
              infected1++;
            }
          }
          if(j < 16) {
            if(people0[i][j + 1] == 'I' || people0[i][j + 1] == 'C') {
              infected1++;
            }
          }
          for(int k = 0; k < infected1; k++) {
            if(Math.random() <= tP) {
              people[i][j] = 'I';
              susceptible--;
              infected++;
              break;
            }
          }
          if(people[i][j] == 'I') {
            infected1 = 0;
            continue;
          }
          if(days >= vD) {
            if(Math.random() < vP) {
              people[i][j] = 'V';
              susceptible--;
              vaccinated++;
            }
          }
          infected1 = 0;
          continue;
        }
        if(people0[i][j] == 'I') {
          double k1 = Math.random();
          if(k1 > sIP && k1 < ((infected > hC * 289) ? sIP + rP : (sIP + rPINB))) {
            people[i][j] = 'R';
            infected--;
            recovered++;
          }
          if(k1 > ((infected > hC * 289) ? sIP + rP : (sIP + rPINB))) {
            people[i][j] = 'C';
            infected--;
            critical++;
          }
          infected1 = 0;
          continue;
        }
        if(people0[i][j] == 'C') {
          double k1 = Math.random();
          if(k1 < dPIC) {
            people[i][j] = 'D';
            critical--;
            dead++;
          }
          if(k1 > dPIC && k1 < dPIC + rPIC) {
            people[i][j] = 'R';
            critical--;
            recovered++;
          }
          if(k1 > dPIC + rPIC) {
            people[i][j] = 'I';
            critical--;
            infected++;
          }
          infected1 = 0;
          continue;
        }
      }
    }
  }
  public void play() {
    Scanner s = new Scanner(System.in);
    while(true) {
      step();
      days++;
      for(int i = 0; i < 17; i++) {
        for(int j = 0; j < 17; j++) {
          System.out.printf("%s ", people[i][j]);
        }
        System.out.print("\n");
      }
      System.out.printf("Susceptible: %.1f%%\nInfected (Mild): %.1f%%\nInfected (Critical): %.1f%%\nRecovered: %.1f%%\nDead: %.1f%%\nVaccinated: %.1f%%\n", susceptible * 100.0 / 289, infected * 100.0 / 289, critical * 100.0 / 289, recovered * 100.0 / 289, dead * 100.0 / 289, vaccinated * 100.0 / 289, susceptible);
      s.nextLine();
      if(infected + critical == 0) {
        reset();
        return;
      }
    }
  }
}

class SIRModel {
  public static void main(String[] args) {
    Model model = new Model(0.34, 5, 0.05, 0.135, 0.04, 0.024, 0.94, 0.15, 0.25);
    // Model model = new Model(0.34, 5, 0.05, 0.135, 0.04, 0.024, 0.94, 1, 0);
    model.play();
  }
}

