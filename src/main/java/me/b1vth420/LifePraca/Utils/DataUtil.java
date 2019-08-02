package me.b1vth420.LifePraca.Utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedHashMap;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class DataUtil {
  
  public static String getDate(long time)
  {
    return dateFormat.format(new Date(time));
  }
  
  public static String getTime(long time)
  {
    return timeFormat.format(new Date(time));
  }
  
  public static String getDurationBreakdown(long millis) {
      if (millis == 0L) {
          return "0";
      }
      final long days = TimeUnit.MILLISECONDS.toDays(millis);
      if (days > 0L) {
          millis -= TimeUnit.DAYS.toMillis(days);
      }
      final long hours = TimeUnit.MILLISECONDS.toHours(millis);
      if (hours > 0L) {
          millis -= TimeUnit.HOURS.toMillis(hours);
      }
      final long minutes = TimeUnit.MILLISECONDS.toMinutes(millis);
      if (minutes > 0L) {
          millis -= TimeUnit.MINUTES.toMillis(minutes);
      }
      final long seconds = TimeUnit.MILLISECONDS.toSeconds(millis);
      if (seconds > 0L) {
          millis -= TimeUnit.SECONDS.toMillis(seconds);
      }
      final StringBuilder sb = new StringBuilder();
      if (days > 0L) {
          sb.append(days);
          final long i = days % 10L;
          if (i == 1L) {
              sb.append("d;");
          }
          else {
              sb.append("d;");
          }
      }
      if (hours > 0L) {
          sb.append(hours);
          final long i = hours % 10L;
          if (i == 1L) {
              sb.append("g;");
          }
          else if (i < 5L) {
              sb.append("g;");
          }
          else {
              sb.append("g;");
          }
      }
      if (minutes > 0L) {
          sb.append(minutes);
          final long i = minutes % 10L;
          if (i == 1L) {
              sb.append("m;");
          }
          else if (i < 5L) {
              sb.append("m;");
          }
          else {
              sb.append("m;");
          }
      }
      if (seconds > 0L) {
          sb.append(seconds);
          final long i = seconds % 10L;
          if (i == 1L) {
              sb.append("s");
          }
          else if (i > 5L) {
              sb.append("s");
          }
          else {
              sb.append("s");
          }
      }
      return sb.toString();
  }

  
  public static long parseDateDiff(String time, boolean future)
  {
    try
    {
      Pattern timePattern = Pattern.compile("(?:([0-9]+)\\s*y[a-z]*[,\\s]*)?(?:([0-9]+)\\s*mo[a-z]*[,\\s]*)?(?:([0-9]+)\\s*w[a-z]*[,\\s]*)?(?:([0-9]+)\\s*d[a-z]*[,\\s]*)?(?:([0-9]+)\\s*h[a-z]*[,\\s]*)?(?:([0-9]+)\\s*m[a-z]*[,\\s]*)?(?:([0-9]+)\\s*(?:s[a-z]*)?)?", 2);
      Matcher m = timePattern.matcher(time);
      int years = 0;
      int months = 0;
      int weeks = 0;
      int days = 0;
      int hours = 0;
      int minutes = 0;
      int seconds = 0;
      boolean found = false;
      while (m.find()) {
        if ((m.group() != null) && (!m.group().isEmpty()))
        {
          for (int i = 0; i < m.groupCount(); i++) {
            if ((m.group(i) != null) && (!m.group(i).isEmpty()))
            {
              found = true;
              break;
            }
          }
          if (found)
          {
            if ((m.group(1) != null) && (!m.group(1).isEmpty())) {
              years = Integer.parseInt(m.group(1));
            }
            if ((m.group(2) != null) && (!m.group(2).isEmpty())) {
              months = Integer.parseInt(m.group(2));
            }
            if ((m.group(3) != null) && (!m.group(3).isEmpty())) {
              weeks = Integer.parseInt(m.group(3));
            }
            if ((m.group(4) != null) && (!m.group(4).isEmpty())) {
              days = Integer.parseInt(m.group(4));
            }
            if ((m.group(5) != null) && (!m.group(5).isEmpty())) {
              hours = Integer.parseInt(m.group(5));
            }
            if ((m.group(6) != null) && (!m.group(6).isEmpty())) {
              minutes = Integer.parseInt(m.group(6));
            }
            if (m.group(7) == null) {
              break;
            }
            if (m.group(7).isEmpty()) {
              break;
            }
            seconds = Integer.parseInt(m.group(7));
            break;
          }
        }
      }
      if (!found) {
        return -1L;
      }
      Calendar c = new GregorianCalendar();
      if (years > 0) {
        c.add(1, years * (future ? 1 : -1));
      }
      if (months > 0) {
        c.add(2, months * (future ? 1 : -1));
      }
      if (weeks > 0) {
        c.add(3, weeks * (future ? 1 : -1));
      }
      if (days > 0) {
        c.add(5, days * (future ? 1 : -1));
      }
      if (hours > 0) {
        c.add(11, hours * (future ? 1 : -1));
      }
      if (minutes > 0) {
        c.add(12, minutes * (future ? 1 : -1));
      }
      if (seconds > 0) {
        c.add(13, seconds * (future ? 1 : -1));
      }
      Calendar max = new GregorianCalendar();
      max.add(1, 10);
      if (c.after(max)) {
        return max.getTimeInMillis();
      }
      return c.getTimeInMillis();
    }
    catch (Exception e) {}
    return -1L;
  }
  
  private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy, HH:mm:ss");
  private static final SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
  private static final LinkedHashMap<Integer, String> values;
  
  static
  {
    (values = new LinkedHashMap(6)).put(Integer.valueOf(31104000), "y");
    values.put(Integer.valueOf(2592000), "msc");
    values.put(Integer.valueOf(86400), "d");
    values.put(Integer.valueOf(3600), "h");
    values.put(Integer.valueOf(60), "min");
    values.put(Integer.valueOf(1), "s");
  }
}
