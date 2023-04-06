import java.util.*;
import java.io.File;
public class date {
    static boolean isValid = true;
    static String ErrorMsg = "ERROR";
    static int dateLimit =0;
    static int ErrorCount=0;
    static boolean leapYear=false;
    static ArrayList<String> errors = new ArrayList<String>(); 
    public static void main(String[] args) throws Exception{
        ArrayList<String> testInputs=new ArrayList<String>(); 
        File file = new File("full_test_data.txt");
        Scanner sc= new Scanner(file);
        Scanner sc2 = new Scanner(System.in);
         
      
         while(sc.hasNextLine()){
           testInputs.add(sc.nextLine());
        } 
         
        System.err.println("Enter a date: ");
        for(int i=0;i<testInputs.size();i++){
            checkInput(testInputs.get(i));
            
        }
        
        while(sc2.hasNextLine()){
        String inputDate= sc2.nextLine();
        //System.out.println(inputDate);
        if(inputDate.compareTo("")==0)break; 
        checkInput(inputDate);
        
        }
        
        //sc.close();
        sc2.close();
        
    }

    public static void checkInput(String inputDate){
        boolean dotCheck=true;
        String day = "";
        String month = "";
        String year = "";
        int select=0;
        char seperator=' ';
        boolean seperatorCheck=true;
        boolean overflow=true;
        boolean inputLength=true;
        boolean toLong=true;
        leapYear=false;
        if(inputDate.length()>1){
        if(Character.isDigit(inputDate.charAt(1))!=true && Character.isAlphabetic(inputDate.charAt(1))!=true){
            
            if(inputDate.length()>1)seperator=inputDate.charAt(1);
        }else if(Character.isDigit(inputDate.charAt(2))!=true && Character.isAlphabetic(inputDate.charAt(2))!=true){
            if(inputDate.length()>2)seperator=inputDate.charAt(2);
        }else{
            if(inputDate.length()>10){
                toLong=false;
            }
        }
        }
        if(inputDate.length()<6){
            inputLength=false;
            isValid=false;
        }else{
        for(int i=0;i<inputDate.length();i++){
            

            if(inputDate.charAt(i)=='\n'){
                overflow=false;
                break;
            }
            if(inputDate.charAt(inputDate.length()-1)==' '){
                overflow=false;
                break;
            }
            if(inputDate.charAt(i) == '.')dotCheck=false;

            if(inputDate.charAt(i)=='/'&&seperator!='/'||inputDate.charAt(i)==' ' &&seperator!=' '|| inputDate.charAt(i)=='-'&&seperator!='-'){
                        
                seperatorCheck=false;
                
            }
            if(inputDate.charAt(i) == seperator &&select<3){
                
                    
                    select++;
                    if(select>=3){
                        overflow=false;
                        break;
                    }
                    i++;
                    
                }else if(inputDate.charAt(i)==seperator&&seperator!='.'&&select<3){
                    select++;
                    if(select>=3){
                        overflow=false;
                        break;
                    }
                    i++;
                }
                if(select==0){
                    day = day + inputDate.charAt(i);
                }else if(select==1){
                    month = month + inputDate.charAt(i);
                }else{
                    year = year + inputDate.charAt(i);
            }
            
        }
    }
        if(year.compareToIgnoreCase("")!=0 && month.compareToIgnoreCase("")!=0 && day.compareToIgnoreCase("")!=0&&dotCheck&&seperatorCheck&&overflow){
        year = yearValid(year);
        month = monthValid(month);
        day = dateValid(day);
        
        String outputDate = day +" "+ month + " "+ year;

        if(isValid==false){
            ErrorCount++;
            outputDate = inputDate +" - INVALID";
            for(int i=0;i < errors.size();i++){
                System.err.println(errors.get(i));
                
            }
        }        
        System.out.println(outputDate);
    }else if(inputLength==false){
        System.err.println("Error! Input is to short, please retry with a string of correct length");
        System.out.println(inputDate + " - INVALID");
    }else if(toLong==false){
        System.err.println("Error! Input is to long, please retry with a string of correct length");
        System.out.println(inputDate + " - INVALID");
    }

    else if(dotCheck==false){
        System.err.println("Error! Input must not contain decimal numbers or fullstops");
        System.out.println(inputDate + " - INVALID");
        ErrorCount++;
        
    }else if(seperatorCheck==false){
        isValid=false;
        System.err.println("ERROR! Entered date must not have differing seperators");
        System.out.println(inputDate + " - INVALID");
        ErrorCount++;
    }else if(overflow==false){
        System.err.println("ERROR! Entered date must have only contain 2 seperators");
        System.out.println(inputDate + " - INVALID");
        ErrorCount++;
    }else{
        //Ask if this is enough of a coverage for negative dates
        System.err.println("Error! Input is not in a day / month / year format, please make sure input all fills all fields correctly.");
        System.out.println(inputDate + " - INVALID");
        ErrorCount++;
    }
    System.err.println("--------------------------------");
    errors.clear();
    isValid=true;
    }

    public static String yearValid(String year){
        for(int i=0;i<year.length();i++){
            if(Character.isDigit(year.charAt(i))==false){
                isValid=false;
                errors.add("Error! Year contains non-numerical inputs");
                return year;
            }
        }
        String out="";
        boolean threeOrMore=true;
        if(year.length()<=1){
            isValid=false;
            errors.add("Error! Year is outside of valid range");
            return year;
        }
        if(year.length()>=3)threeOrMore=false;
        //System.out.println(threeOrMore);
        if("a".compareToIgnoreCase(year)<50&&"a".compareToIgnoreCase(year)>39){
        int value = Integer.parseInt(year);
        if(value > 99 && value < 1753 || value > 3000){
            isValid=false;
            errors.add("Error! Year is outside of valid range");
            return year;
        }
        if(value<=99&&threeOrMore){
            
            if(value>49){
                value = value+1900;
            }else{
                value= value+2000;
            }
            out=out+value;
        }else if(value>=1753&&year.charAt(0)!='0'){
        out=out+value;
        }else{
            isValid=false;
            errors.add("Error! Year is outside of valid range");
            return year;
        }
    if(value%4==0){
        leapYear=true;
        if(value%100==0){
            if(value%400==0){
                leapYear=true;
            }else{
                leapYear=false;
            }
        }
    }
    return out;
    }else{
        isValid=false;
        errors.add("Error! Year entered isn't a numerical value");
        return year;
    }    
    }

    public static String monthValid(String month){
        String out ="a";
        if(month.length()>3){
            isValid = false;
            errors.add("Error! Month String length is greater than allowed range");
            return month;
        }else{
        if(out.compareToIgnoreCase(month)<50&&out.compareToIgnoreCase(month)>39){
            if(month.length()<=2){
                String allNumbersCheck=""+month.charAt(month.length()-1);
            if("a".compareToIgnoreCase(allNumbersCheck)<50&&"a".compareToIgnoreCase(allNumbersCheck)>39){
            int value = Integer.parseInt(month);
            if(value>12 || value<1){
                isValid = false;
                errors.add("Error! Month is outside of numerical month range");
                return month;
            }else{
                if(value==1){
                    out="Jan";
                    dateLimit = 31;
                }else if(value==2){
                    out="Feb";
                    if(leapYear)dateLimit = 29;
                    else dateLimit = 28;
                }else if(value==3){
                    out="Mar";
                    dateLimit = 31;
                }else if(value==4){
                    out="Apr";
                    dateLimit = 30;
                }else if(value==5){
                    out="May";
                    dateLimit = 31;
                }else if(value==6){
                    out="Jun";
                    dateLimit = 30;
                }else if(value==7){
                    out="Jul";
                    dateLimit = 31;
                }else if(value==8){
                    out="Aug";
                    dateLimit = 31;
                }else if(value==9){
                    out="Sep";
                    dateLimit = 30;
                }else if(value==10){
                    out="Oct";
                    dateLimit = 31;
                }else if(value==11){
                    out="Nov";
                    dateLimit = 30;
                }else if(value==12){
                    out="Dec";
                    dateLimit = 31;
                }
            }
            }
        }
        }else{
            if(month.length()==3){
            boolean allSameCase=true;
            if(Character.isLowerCase(month.charAt(0)) && Character.isLowerCase(month.charAt(1))&&Character.isLowerCase(month.charAt(2))||Character.isUpperCase(month.charAt(0)) && Character.isUpperCase(month.charAt(1))&&Character.isUpperCase(month.charAt(2))||Character.isUpperCase(month.charAt(0)) && Character.isLowerCase(month.charAt(1))&&Character.isLowerCase(month.charAt(2))){
                    allSameCase=true;
                }else{
                    allSameCase=false;  
                }
            if(allSameCase==false){
            isValid=false;
            errors.add("ERROR! All characters must be of the same case");
            return month;
            }
            if(month.compareToIgnoreCase("Jan")==0){
                out = "Jan";
                dateLimit = 31;
            }else if(month.compareToIgnoreCase("Feb")==0 ){
                out = "Feb";
                if(leapYear)dateLimit=29;
                else dateLimit = 28;
            }else if(month.compareToIgnoreCase("Mar")==0){
                out = "Mar";
                dateLimit = 31;
            }
            else if(month.compareToIgnoreCase("Apr")==0){
                out = "Apr";
                dateLimit = 30;
            }else if(month.compareToIgnoreCase("May")==0){
                out = "May";
                dateLimit = 31;

            }else if(month.compareToIgnoreCase("Jun")==0){
                out = "Jun";
                dateLimit = 30;
            }else if(month.compareToIgnoreCase("Jul")==0 ){
                out = "Jul";
                dateLimit = 31;
            }else if(month.compareToIgnoreCase("Aug")==0 ){
                out = "Aug";
                dateLimit = 31;
            }else if(month.compareToIgnoreCase("Sep")==0){
                out = "Sep";
                dateLimit = 30;
            }else if(month.compareToIgnoreCase("Oct")==0){
                out = "Oct";
                dateLimit = 31;
            }else if(month.compareToIgnoreCase("Nov")==0){
                out = "Nov";
                dateLimit = 30;
            }else if(month.compareToIgnoreCase("Dec")==0){
                out = "Dec";
                dateLimit = 31;
            }
        }
        }
        if(out.compareToIgnoreCase("a")==0){
            out=month;
            isValid=false;
            errors.add("ERROR! Month entered isnt a valid month");
        }
        return out;
        }
    }

    public static String dateValid(String date){
    String out="0";
    if(date.length()>2){
        isValid=false;
        errors.add("Error! Inputed String is to long, please ensure days are between 1 to 2 characters long");
        return date;
    }
    if("1".compareToIgnoreCase(date)>=-9 && "1".compareToIgnoreCase(date)<9){
    //System.out.println("1".compareToIgnoreCase(date));
    int value=99;
    if(Character.isDigit(date.charAt(0))){
        if(date.length()>1){
          if(Character.isDigit(date.charAt(1)) && Character.isDigit(date.charAt(0))){
            value = Integer.parseInt(date);
          }  
        }else{ 
            if(date.length()<1){
            System.out.println(date.length());
            value = Integer.parseInt(date);
        }
        }
    }
    if(value>31 ||value<1){
        isValid=false;
        out=date;
        errors.add("Error! Day is outside of valid date range");
    }
    else{
        if(value<=dateLimit){
            if(date.length()<2){
                out=out+date;
            }else{
                out=date;
            }
        }else if(dateLimit>0){
           isValid=false;
           errors.add("Error! Day is outside of valid date range");
           out=date;
        }
    }
    if(dateLimit==0){
        out = date;
    }
    return out;
    }else {
         if(dateLimit==0){
        return date;
    }else{
    isValid=false;
    errors.add("Error! Date entered isn't a numerical value");
    return date;
    }
    }
    }
}
