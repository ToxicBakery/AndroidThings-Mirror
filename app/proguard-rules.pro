-keepattributes Signature

# Retrofit
-dontnote retrofit2.Platform
-dontwarn retrofit2.Platform$Java8
-keepattributes Signature
-keepattributes Exceptions

#ical4j
-keep,includedescriptorclasses class net.fortuna.ical4j.util.MapTimeZoneCache
