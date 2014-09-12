 package e1.tools.asbuilder;
 
 import e1.tools.asbuilder.utils.FileUtils;
 
 public class CreateProperty
 {
   public static void main(String[] args)
   {
     FileUtils.createProperty(args[0], args[1], args[2], args[3], args[4].equalsIgnoreCase("1"), args[5].equalsIgnoreCase("1"), args[6], 1);
   }
}
