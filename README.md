# Igniter

Igniter is a Java library designed to parse command line arguments in an easy way.

It contains two main classes:

**Option.java**: 	`Option` allows you to define the name or names of the desired option, its default value and the expected type of that value.
					 Also, it lets you add a description of its purpose, used when calling `getHelpDescription()` in the following class.
				  
**ArgParser.java**: `ArgParser` is where `Option` objects are added. When an array of `String` objects is given, it parses all its data and stores the 
					 obtained values into its corresponding options and returns the number of arguments parsed or throws an exception if it founds any errors. These values can be retrieved after parsing by just providing the name of the option you want to get its value. A type cast is needed to assing this value to a new variable.
					 Supports `boolean`, `byte`, `char`, `double`, `float`, `int`, `long` and `String` types. 
					 Calling `getHelpDescription()` will give you a help-like message stored in a `String` similar to the commonly used help commands in usual console applications.

## Examples

**Parsing 2 arguments (int, String)**

```java
public class Example1 {

    public static void main(String[] args) {
        
        // Default values
        int defaultVal1 = 100;
        String defaultVal2 = "abcdef";
        
        // Create new Option(s)
        Option opt1 = new Option("--option1", "-opt1", defaultVal1, "Description for option 1.", int.class);
        Option opt2 = new Option("--option2", "-opt2", defaultVal2, "Description for option 2.", String.class);

        // Create new ArgParser
        ArgParser argP = new ArgParser();
        argP.addOption(opt1);
        argP.addOption(opt2);

        // Parse arguments
        int parsedArgs=0;
        try {
            parsedArgs= argP.parse(args);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // Read argument(s) value(s)
        int opt1Val= (int)argP.getValue("--option1");
        String opt2Val= (String)argP.getValue("--option2");
        
        // Print value(s)
        System.out.println("Parsed arguments: " + parsedArgs);
        System.out.println("Option 1: " + opt1Val);
        System.out.println("Option 2: " + opt2Val);
    }
}

```

Outputs:
```
java Example1 --option1 150 --option2 "vwxyz"

	Parsed arguments: 2
	Option 1: 150
	Option 2: vwxyz
	
```
```
java Example1 --option1 150

	Parsed arguments: 1
	Option 1: 150
	Option 2: abcdef
	
```
```
java Example1

	Parsed arguments: 0
	Option 1: 100
	Option 2: abcdef
	
```

**Parsing 1 optional argument (boolean) and 1 required (float)**
**(Printing help message)**
```java
public class Example2 {

    public static void main(String[] args) {
        
        // Default values
        boolean defaultVal1 = false;
        float defaultVal2 = 123.321f;
        
        // Create new Option(s)
        Option opt1 = new Option("--boolean", "-b", defaultVal1, "Boolean option.", true, boolean.class);
        Option opt2 = new Option("--float", "-f", defaultVal2, "Float option.", float.class);

        // Create new ArgParser
        ArgParser argP = new ArgParser();
        argP.addOption(opt1);
        argP.addOption(opt2);
        
        // Print help message
        System.out.println(argP.getHelpDescription());
        System.out.println("------------------------");
        
        // Parse arguments
        int parsedArgs=0;
        try {
            parsedArgs= argP.parse(args);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // Read argument(s) value(s)
        boolean opt1Val= (boolean)argP.getValue("-b");
        float opt2Val= (float)argP.getValue("-f");
        
        
        
        // Print value(s)
        System.out.println("Parsed arguments: " + parsedArgs);
        System.out.println("Boolean: " + opt1Val);
        System.out.println("Float  : " + opt2Val);
    }
}
```
Outputs:
```
java Example2 --boolean --float 999.666

		--boolean, -b	:Boolean option.
						:Default: false
						[REQUIRED]

		--float, -f		:Float option.
						:Default: 123.321

	------------------------
	Parsed arguments: 2
	Boolean: true
	Float  : 999.666
```
```
java Example2 --boolean

		--boolean, -b	:Boolean option.
						:Default: false
						[REQUIRED]

		--float, -f		:Float option.
						:Default: 123.321

	------------------------
	Parsed arguments: 1
	Boolean: true
	Float  : 123.321
```
```
java Example2 --float 999.666

		--boolean, -b	:Boolean option.
						:Default: false
						[REQUIRED]

		--float, -f		:Float option.
						:Default: 123.321

	------------------------
	[!] The following required arguments are missing:
		--boolean, -b

	Parsed arguments: 0
	Boolean: false
	Float  : 123.321
```


## Author

**Samoxiaki**
	Email: Samoxiaki@yahoo.com
	[Github](https://github.com/Samoxiaki)
 	[Twitter](https://twitter.com/Samoxiaki)
 	
## License

This project is licensed under the BSD 2-Clause License - see the [LICENSE.md](LICENSE.md) file for details

