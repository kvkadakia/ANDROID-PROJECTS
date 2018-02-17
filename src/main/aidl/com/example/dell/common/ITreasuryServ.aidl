// ITreasuryServ.aidl
package com.example.dell.common;

// Declare any non-default types here with import statements

interface ITreasuryServ {
     List<String> monthlyCash(String s);
     List<String> dailyCash(String d , String m, String y, String w);
     String yearlyAvg(String s);
}
