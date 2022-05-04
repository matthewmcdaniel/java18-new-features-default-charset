package me.matthewmcdaniel;

import java.io.FileWriter;
import java.nio.charset.Charset;

// Link to JEP: https://openjdk.java.net/jeps/400

/* Many standard Java APIs defer to the standard JDK charset, which itself is determined by the host's OS and locale.
 *  To get around this, many developers specify the charset when invoking these APIs.
 *  For instance, the FileWriter class constructor has an argument for specifying the charset.
 *  Even with these additions, many developers rely on the JDK's default charset, which itself is
 *  not standard across machines. A Windows machine with an english locale will have a different charset
 *  than a Windows machine with a Japanese locale. In JDK 18, This has been solved by making UTF-8
 *  the default charset UTF-8 across all programs. To demonstrate this, look at the following code:
 *
 *  1) FileWriter fw = new FileWriter("test.txt"); // cp1252 in Java 17 and below (machine-dependent)
 *
 *  2) FileWriter fw = new FileWriter("test.txt"); // UTF-8 in Java 18
 *
 *  Line number 1 was executed with a Java 15, line number 2 was executed with Java 18.
 *
 *  The following program prints out the default charset and native encoding for this JVM and machine respectively.
 *
 *  Prerequisites: Two JDKs, Java 18 and Java 17 or below.
 *
 *  Usage: [Java 18 (Windows) Assuming JAVA_HOME points to JDK 18]: java .\src\App.java
 *
 *         Then compare the output to running the program on versions below Java 18.
 *
 *         [Java 17 and below (Windows)]: 'C:\Program Files\Java\<JDK>\bin\java.exe' .\src\App.java
 */

public class Main {
    public static void main(String[] args) throws Exception {

        /*
         * When running this line on Java 18, it will yield 'UTF-8'. On versions below Java 18, it will yield the
         * default JDK charset.
         */

        System.out.println("default charset = " + Charset.defaultCharset());

        String encoding = System.getProperty("native.encoding");  // Populated on Java 18 and later
        String encoding2 = System.getProperty("file.encoding");
        System.out.println("native.encoding = " + encoding);
        System.out.println("file.encoding = " + encoding2);

        // If you need to use the native encoding of your OS, the following is an example of how you can accomplish that.
        // This is useful for backwards compatibility.
        Charset cs = (encoding != null) ? Charset.forName(encoding) : Charset.defaultCharset();
        var writer = new FileWriter("file.txt", cs);

        /*
         * The file.encoding and native.encoding system properties
         *
         * With Java 18, developers can now change the default encoding system property file.encoding. Note that
         * the only two supported options are COMPAT and UTF-8. COMPAT will determine the encoding based on how Java
         * determined it prior to Java 18.
         *
         * Example: java '-Dfile.encoding=COMPAT' Main.java
         *
         * Then compare it with the output of and old JDK.
         *
         * '<PATH TO OLD JDK>\bin\java.exe' Main.java
         *
         */

    }
}
