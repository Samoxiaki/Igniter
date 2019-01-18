/* 
 * Copyright (c) 2019, Samoxiaki
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * * Redistributions of source code must retain the above copyright notice, this
 *   list of conditions and the following disclaimer.
 * * Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package com.samoxiaki.igniter.core;

import com.samoxiaki.igniter.exceptions.MissingReqArgsException;
import com.samoxiaki.igniter.exceptions.NotEnoughArgsException;
import com.samoxiaki.igniter.exceptions.NotSupportedTypeException;
import com.samoxiaki.igniter.exceptions.RepeatedArgsException;
import com.samoxiaki.igniter.exceptions.UnknownArgException;
import java.util.ArrayList;

/**
 *
 * @author Samoxiaki
 */
public class ArgParser {

    private ArrayList<Option> options;

    public ArgParser() {

        this.options = new ArrayList<>();
    }

    public int checkOption(Option opt) {
        return options.indexOf(opt);
    }

    public int checkOption(String optName) {
        int index = 0;
        for (Option opt : options) {
            if (opt.equals(optName)) {
                return index;
            }
            index++;
        }
        return -1;
    }

    public boolean addOption(Option opt) {
        if (this.checkOption(opt) != -1) {
            return false;
        }
        return options.add(opt);
    }

    public boolean removeOption(Option opt) {
        return options.remove(opt);
    }

    public Object getValue(String optName) {
        int optIndex = this.checkOption(optName);
        if (optIndex != -1) {
            return options.get(optIndex).getValue();
        }
        return null;
    }

    public boolean setValue(String optName, Object value) {
        int optIndex = this.checkOption(optName);
        if (optIndex != -1) {
            options.get(optIndex).setValue(value);
            return true;
        }
        return false;
    }

    public boolean reset(String optName) {
        int optIndex = this.checkOption(optName);
        if (optIndex != -1) {
            options.get(optIndex).reset();
            return true;
        }
        return false;
    }

    public void resetAll() {
        options.forEach((opt) -> {
            opt.reset();
        });
    }

    public ArrayList<Option> getRequiredOptions() {
        ArrayList<Option> requiredOptions=new ArrayList<>();
        
        for(Option opt : options){
            if(opt.isRequired())
            requiredOptions.add(opt);
        }
        return requiredOptions;
    }
    
    public Option getOption(String optName){
        int optIndex = this.checkOption(optName);
        if (optIndex != -1) {
            return options.get(optIndex);
        }
        return null;
    }
    public boolean setOption(String optName, Option opt){
        int optIndex = this.checkOption(optName);
        if (optIndex != -1) {
            options.set(optIndex, opt);
            return true;
        }
        return false;
    }
    public boolean setOption(Option opt){
        int optIndex = this.checkOption(opt);
        if (optIndex != -1) {
            options.set(optIndex, opt);
            return true;
        }
        return false;
    }
    public ArrayList<Option> getOptions() {
        return options;
    }

    public void setOptions(ArrayList<Option> options) {
        this.options = options;
    }
    
    public String getHelpDescription(){
        StringBuilder sb= new StringBuilder();
        for(Option opt : options){
            if(opt.hasAuxName()){
               sb.append("\t").append(opt.getName()).append(", ").append(opt.getAuxName()).append("\t|").append(opt.getDescription());
            }
            else{
               sb.append("\t").append(opt.getName()).append("\t\t|").append(opt.getDescription());
            }
            if(opt.getDescription().length()!=0 && opt.getDescription()!=null){
                   sb.append("\n\t\t\t|");
               }
            sb.append("Default: ").append(opt.getDefaultValue());
            if(opt.isRequired() || opt.isStandalone()){
                sb.append("\n\t\t\t");
                if(opt.isRequired()){
                        sb.append("[REQUIRED]");
                }
                if(opt.isStandalone()){
                        sb.append("[STANDALONE]");
                }
            }
            sb.append("\n\n");
        }
        return sb.toString();
    }
    
    public int parse(String[] args) throws UnknownArgException, NotEnoughArgsException, RepeatedArgsException, NotSupportedTypeException, MissingReqArgsException{
        resetAll();
        ArrayList<Option> requiredOptions = getRequiredOptions();
        ArrayList<Option> usedOptions = new ArrayList<>();
        int index=0;
        int parsed=0;
        boolean parsedStandalone=false;
        if(args.length>0){
            while(index<args.length){
                int optIndex=checkOption(args[index]);
                boolean parsedArg=false;
                if(optIndex==-1){
                    resetAll();
                    throw new UnknownArgException("[!] Unknown argument:\n\t" + args[index]);
                }
                if(options.get(optIndex).getType().equals(boolean.class)){ // Takes no more args than itself
                    boolean value= !(boolean)options.get(optIndex).getDefaultValue();
                    options.get(optIndex).setValue(value);
                    parsedArg=true;
                }
                if(!parsedArg){ // Takes more than 1 arg
                    
                    // TODO: Enable more args taken by an option
                    // 1 extra arg
                    if((index+1)<args.length){
                        index++;
                    }
                    else{
                        resetAll();
                        throw new NotEnoughArgsException("[!] Not enough arguments given for:\n\t" + options.get(optIndex).getName() + ", " + options.get(optIndex).getAuxName()); 
                    }
                    if(options.get(optIndex).getType().equals(byte.class)){
                        byte value= Byte.parseByte(args[index]);
                        options.get(optIndex).setValue(value);
                        parsedArg=true;
                    }
                    if(options.get(optIndex).getType().equals(char.class)){
                        char value= args[index].charAt(0);
                        options.get(optIndex).setValue(value);
                        parsedArg=true;
                    }
                    if(options.get(optIndex).getType().equals(double.class)){
                        double value= Double.valueOf(args[index]);
                        options.get(optIndex).setValue(value);
                        parsedArg=true;
                    }
                    if(options.get(optIndex).getType().equals(float.class)){
                        float value= Float.valueOf(args[index]);
                        options.get(optIndex).setValue(value);
                        parsedArg=true;
                    }
                    if(options.get(optIndex).getType().equals(int.class)){
                        int value= Integer.valueOf(args[index]);
                        options.get(optIndex).setValue(value);
                        parsedArg=true;
                    }
                    if(options.get(optIndex).getType().equals(long.class)){
                        long value= Long.valueOf(args[index]);
                        options.get(optIndex).setValue(value);
                        parsedArg=true;
                    }
                    if(options.get(optIndex).getType().equals(String.class)){
                        options.get(optIndex).setValue(args[index]);
                        parsedArg=true;
                    }
                }
                if(parsedArg){
                    if(usedOptions.contains(options.get(optIndex))){
                        resetAll();
                        throw new RepeatedArgsException("[!] Repeated argument:\n\t" +  options.get(optIndex).getName() + ", " + options.get(optIndex).getAuxName()); 
                    }
                    else{
                        usedOptions.add(options.get(optIndex));
                    }
                    if(options.get(optIndex).isStandalone() && parsedStandalone==false){
                        parsedStandalone=true;
                    }
                    requiredOptions.remove(options.get(optIndex));
                    index++;
                    parsed++;
                }
                else{
                    resetAll();
                    throw new NotSupportedTypeException("[!] Not supported type for:\n\t" +  options.get(optIndex).getName() + ", " + options.get(optIndex).getAuxName() + ": " + options.get(optIndex).getType().toString());
                } 
            }
            
        }
        if(parsedStandalone && parsed==1){
            return parsed;
        }
        if(requiredOptions.size()>0){
                resetAll();
                StringBuilder sb= new StringBuilder();
                for(Option opt : requiredOptions){
                    sb.append("\t").append(opt.getName()).append(", ").append(opt.getAuxName()).append("\n");
                }
                throw new MissingReqArgsException("[!] The following required arguments are missing:\n" +  sb.toString());
        }
        return parsed;
    }
}
