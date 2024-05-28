##
##Disable loggin
##

#Disable Android logging:

#Disable All
#-assumenosideeffects class android.util.Log{*;}
#Disable Some
-assumenosideeffects class android.util.Log {
  public static *** v(...);
  public static *** d(...);
}