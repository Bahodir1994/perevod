package uz.perevods.perevod.component.utils;

import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@Component
public class Utils {

    public static String nullClear(String s) {
        s = ((s == null || s.equals("null") || s.equals("")) ? "" : s);
        return s;
    }

    public static String nullClear(Object s) {
        s = ((s == null || s.equals("null") || s.equals("")) ? "" : s);
        return s.toString();
    }

    public static String nullClear0(String s) {
        s = ((s == null || s.equals("null") || s.equals("")) ? "0" : s);
        return s;
    }

    public static String nullClear1(String s) {
        s = ((s == null || s.equals("null") || s.equals("")) ? "0" : s);
        return s;
    }

    public static String nullClear00(String s) {
        s = ((s == null || s.equals("null") || s.equals("")) ? "0.0" : s);
        return s;
    }

    public static String round(String value, int places) {
        if (places < 0) throw new IllegalArgumentException();
        if (value.equals("")) return "";
        if (value.equals("0")) return "0";
        else {
            BigDecimal bd = BigDecimal.valueOf(Double.parseDouble(value));
            bd = bd.setScale(places, RoundingMode.HALF_UP);
            return String.valueOf(bd.doubleValue());
        }
    }

    public static Integer nullClearI0(String s) {
        s = ((s == null || s.equals("null") || s.equals("")) ? "0" : s);
        return Integer.valueOf(s);
    }

    public static String nullClearIS0(Integer s) {
        s = (s == null ? 0 : s);
        return String.valueOf(s);
    }

    public static String formatDecimal(Double price) {
        DecimalFormat formatter = new DecimalFormat("###,###,###.00");
        if (price == 0.0) return "0,0";
        else
            return formatter.format(price);
    }

    public static String formatDecimal2(Double price) {
        DecimalFormat formatter = new DecimalFormat("###,###,###.00");
        if (price == 0.0) return "0,0";
        else
            return formatter.format(price);
    }

    public static boolean isNumeric(String str) {
        try {
            double d = Double.parseDouble(str);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public static String toDate(Date t) {
        if (t != null) {
            Locale l = new Locale("ru", "");
            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy", l);
            return sdf.format(t);
        } else {
            return "";
        }
    }

    public static String toDate00(Date t) {
        if (t != null) {
            Locale l = new Locale("ru", "");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", l);
            return sdf.format(t);
        } else {
            return "";
        }
    }

    public static boolean isDate(String dateStr) {
        boolean result = true;
        SimpleDateFormat sdf = null;
        if (dateStr.contains("-"))
            sdf = new SimpleDateFormat("yyyy-MM-dd");
        else if (dateStr.contains("."))
            sdf = new SimpleDateFormat("dd.MM.yyyy");
        if (sdf != null) {
            sdf.setLenient(false);
            try {
                sdf.parse(dateStr);
            } catch (ParseException e) {
                result = false;
            }
            if (dateStr.length() != 10)
                result = false;
        } else result = false;
        return result;
    }

    public static String RepMus(String in) {
        StringBuffer out = new StringBuffer(); // Used to hold the output.
        char current; // Used to reference the current character.
        if (in == null || ("".equals(in))) return ""; // vacancy test.
        for (int i = 0; i < in.length(); i++) {
            current = in.charAt(i); //NOTE: No IndexOutOfBoundsException caught here; it should not happen.
            if ((current == 0x9) ||
                    (current == 0xA) ||
                    (current == 0xD) ||
                    ((current >= 0x20) && (current <= 0xD7FF)) ||
                    ((current >= 0xE000) && (current <= 0xFFFD)) ||
                    ((current >= 0x10000) && (current <= 0x10FFFF)))
                out.append(current);
        }
        String SS = out.toString().replace("&nbsp;", "");
        SS = SS.replace("\u00a0", "").trim();
        return SS;
    }

    public static String getEncodedFilename(HttpServletRequest request, String fileName) throws UnsupportedEncodingException {
        request.setCharacterEncoding("utf-8");
        if (request.getHeader("User-Agent").contains("Opera") || !request.getHeader("User-Agent").contains("MSIE")) {
            return new String(fileName.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1); //mozilla
        } else {
            StringBuilder encoded_filename = new StringBuilder();
            char[] hexdigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'}; //MSIE
            byte[] fileNameBytes = fileName.getBytes(StandardCharsets.UTF_8);
            for (byte fileNameByte : fileNameBytes) {
                if ((fileNameByte | 0x7F) == 0xFFFFFFFF) {
                    encoded_filename.append('%');
                    encoded_filename.append(hexdigits[(fileNameByte & (15 * 16)) / 16]);
                    encoded_filename.append(hexdigits[fileNameByte & 15]);
                } else {
                    encoded_filename.append((char) fileNameByte);
                }
            }
            return encoded_filename.toString();
        }
    }

    public String generateRandomNumber() {
        int randomNumber = (int) (Math.random() * 99999) + 10000;
        String number = String.valueOf(randomNumber);
        return number;
    }

    public static class MoneyInWords {
        private static final String[][] dig1 = {{"бир", "икки", "уч", "тўрт", "беш", "олти", "етти", "саккиз", "тўққиз"},
                {"бир", "икки"}}; //dig[0] - female, dig[1] - male
        private static final String[] dig10 = {"ўн", "ўн бир", "ўн икки", "ўн уч", "ўн тўрт",
                "ўн беш", "ўн олти", "ўн етти", "ўн саккиз", "ўн тўққиз"};
        private static final String[] dig20 = {"йигирма", "ўттиз", "қириқ", "эллик",
                "олтмиш", "етмиш", "саксон", "тўқсон"};
        private static final String[] dig100 = {"бир юз", "икки юз", "уч юз", "тўрт юз", "беш юз",
                "олти юз", "етти юз", "саккиз юз", "тўққиз юз"};
        private static final String[][] leword = {{"тийин", "тийин", "тийин", "0"},
                {"сўм", "сўм", "сўм", "1"},
                {"минг", "минг", "минг", "0"},
                {"миллион", "миллион", "миллион", "1"},
                {"миллиард", "миллиард", "миллиард", "1"},
                {"триллион", "триллион", "триллион", "1"}};

        //рекурсивная функция преобразования целого числа num в рубли
        private static String num2words(long num, int level) {
            StringBuilder words = new StringBuilder(50);
            if (num == 0) words.append("ноль ");         //исключительный случай
            int sex = leword[level][3].indexOf("1") + 1; //не красиво конечно, но работает
            int h = (int) (num % 1000);    //текущий трехзначный сегмент
            int d = h / 100;              //цифра сотен
            if (d > 0) words.append(dig100[d - 1]).append(" ");
            int n = h % 100;
            d = n / 10;                   //цифра десятков
            n = n % 10;                   //цифра единиц
            switch (d) {
                case 0:
                    break;
                case 1:
                    words.append(dig10[n]).append(" ");
                    break;
                default:
                    words.append(dig20[d - 2]).append(" ");
            }
            if (d == 1) n = 0;              //при двузначном остатке от 10 до 19, цифра едициц не должна учитываться
            switch (n) {
                case 0:
                    break;
                case 1:
                case 2:
                    words.append(dig1[sex][n - 1]).append(" ");
                    break;
                default:
                    words.append(dig1[0][n - 1]).append(" ");
            }
            switch (n) {
                case 1:
                    words.append(leword[level][0]);
                    break;
                case 2:
                case 3:
                case 4:
                    words.append(leword[level][1]);
                    break;
                default:
                    if ((h != 0) || ((h == 0) && (level == 1)))  //если трехзначный сегмент = 0, то добавлять нужно только "рублей"
                        words.append(leword[level][2]);
            }
            long nextnum = num / 1000;
            if (nextnum > 0) {
                return (num2words(nextnum, level + 1) + " " + words).trim();
            } else {
                return words.toString().trim();
            }
        }

        //функция преобразования вещественного числа в рубли-копейки
        //при значении money более 50-70 триллионов рублей начинает искажать копейки, осторожней при работе такими суммами
        public static String inwords(double money) {
            if (money < 0.0) return "error: отрицательное значение";
            String sm = String.format("%.2f", money);
            String skop = sm.substring(sm.length() - 2);    //значение копеек в строке
            int iw;
            switch (skop.substring(1)) {
                case "1":
                    iw = 0;
                    break;
                case "2":
                case "3":
                case "4":
                    iw = 1;
                    break;
                default:
                    iw = 2;
            }
            long num = (long) Math.floor(money);
            if (num < 1000000000000000l) {
                return num2words(num, 1) + " " + skop + " " + leword[0][iw];
            } else
                return "error: слишком много сум " + skop + " " + leword[0][iw];
        }
    }

    public static String toCase_userName(String user_name) {
        if (user_name == null) return "";
        String strYangi = "";
        String[] ab = user_name.split(" ");
        for (int i = 0; i < ab.length; i++) {
            if (!strYangi.equals("")) {
                strYangi = strYangi + " " + ab[i].substring(0, 1).toUpperCase() + ab[i].substring(1).toLowerCase();
            } else {
                strYangi = ab[i].substring(0, 1).toUpperCase() + ab[i].substring(1).toLowerCase();
            }

        }

        return strYangi;
    }


    public static String toFIO(String fullName) {
        assert (fullName != null);
        fullName = fullName.replaceAll("  ", " ");
        String[] nameParts = fullName.trim().split("\\s+");
        StringBuilder nameBuilder = new StringBuilder();

        for (int i = 0; i < nameParts.length; i++) {
            if (i == 1) {
                nameBuilder.append(' ');
            }
            String part = nameParts[i];
            if (i == 0) {
                nameBuilder.append(part.substring(0, 1).toUpperCase() + part.substring(1).toLowerCase());
            } else {
                char middleInitial = part.charAt(0);
                nameBuilder.append(String.format("%c.", middleInitial));
            }
        }
        return nameBuilder.toString();
    }

    public static String toFIO1(String fullName) {
        assert (fullName != null);
        fullName = fullName.replaceAll("  ", " ");
        String[] nameParts = fullName.trim().split("\\s+");
        StringBuilder nameBuilder = new StringBuilder();

        for (int i = 0; i < nameParts.length; i++) {
            String part = nameParts[i];
            nameBuilder.append(part.substring(0, 1).toUpperCase() + part.substring(1).toLowerCase());
            nameBuilder.append(' ');
        }
        return nameBuilder.toString();
    }

    public static String toFIO2(String fullName) {
        assert (fullName != null);
        if (!fullName.equals("")) {
            fullName = fullName.replaceAll("  ", " ");
            String[] nameParts = fullName.trim().split("\\s+");
            StringBuilder nameBuilder = new StringBuilder();
            Integer n = nameParts.length;
            if (nameParts.length == 2) n = nameParts.length;
            if (nameParts.length == 3) n = nameParts.length - 1;
            if (nameParts.length == 4) n = nameParts.length - 2;
            if (nameParts.length == 5) n = nameParts.length - 3;
            for (int i = n - 1; i > -1; i--) {
                if (i == 0) {
                    nameBuilder.append(' ');
                }
                String part = nameParts[i];
                if (i == 0) {
                    nameBuilder.append(part.substring(0, 1).toUpperCase() + part.substring(1).toLowerCase());
                } else {
                    char middleInitial = part.charAt(0);
                    nameBuilder.append(String.format("%c.", middleInitial));
                }
            }
            return nameBuilder.toString();
        } else return "";

    }

    public static String tecDate() {
        Locale l = new Locale("ru", "");
        Date aDate = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy", l);
        String formattedDate = formatter.format(aDate);
        return formattedDate;
    }

    public static String tecDate00() {
        Locale l = new Locale("ru", "");
        Date aDate = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", l);
        String formattedDate = formatter.format(aDate);
        return formattedDate;
    }

    public static String toDB2SQL(Timestamp t) {
        if (t != null) {
            Locale l = new Locale("ru", "");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH.mm.ss.000000", l);
            return sdf.format(t);
        } else {
            return "&nbsp;";
        }
    }

    public static String toDB2(Date t) {
//        String m= t.toString().substring((t.toString().indexOf(".")+1),t.toString().length());
        if (t != null) {
            Locale l = new Locale("ru", "");
            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss", l);
            return sdf.format(t);
        } else {
            return "&nbsp;";
        }
    }

}
