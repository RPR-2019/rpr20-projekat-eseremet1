package ba.unsa.etf.rpr.project;

import java.util.Random;

public interface NewPassword {

    default String generatePassword() {
        String newPassword="";
        String possibleCharacters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";
        while(true) {
            StringBuilder salt = new StringBuilder();
            Random random = new Random();
            //generise neko od 8 cifara
            while (salt.length() < 7) { //zbog uslova
                int index = (int) (random.nextFloat() * possibleCharacters.length());
                salt.append(possibleCharacters.charAt(index));
            }
            newPassword = salt.toString();


            boolean small=false, uppercase = false, number = false;
            //a sadrži barem jedno veliko slovo, jedno malo slovo i jednu cifru

            for(int i=0; i<newPassword.length(); i++) {
                if(newPassword.charAt(i)>= 'A' && newPassword.charAt(i)<= 'Z') {
                    uppercase=true;
                }
                else if(newPassword.charAt(i)>= 'a' && newPassword.charAt(i)<= 'z') {
                    small=true;
                }
                else if(newPassword.charAt(i)>= '0' && newPassword.charAt(i)<= '9') {
                    number=true;
                }
                else continue;
            }

            if(uppercase && small && number) break;
        }
        String specijalni="!#$%&/()=?*~";
        Random random = new Random();
        int index=(int) (random.nextFloat() * specijalni.length());

        newPassword+=possibleCharacters.charAt(index);
        return newPassword;
    }
}
