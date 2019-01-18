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

import java.util.Objects;

/**
 *
 * @author Samoxiaki
 */
public class Option {

    private String name;
    private String auxName;
    private Object defaultValue;
    private Object value;
    private Class type;
    private String description;
    private boolean required;
    private boolean standalone;

    /**
     * Constructs a newly allocated {@code Option} object with no attributes.
     * Attributes have to be set manually.
     */
    public Option() {
        this.name = null;
        this.auxName = null;
        this.defaultValue = null;
        this.value = null;
        this.type = Object.class;
        this.description = null;
        this.required = false;
        this.standalone=false;
    }

    /**
     * Constructs a newly allocated {@code Option} object with the given
     * attributes.
     *
     * @param name {@code Option} main name.
     * @param auxName {@code Option} auxiliar name.
     * @param defaultValue {@code Option} default value.
     * @param description a description of the option.
     * @param type stored value class type.
     */
    public Option(String name, String auxName, Object defaultValue, String description, Class type) {
        this.name = name;
        this.auxName = auxName;
        this.defaultValue = defaultValue;
        this.value = defaultValue;
        this.type = type;
        this.description = description;
        this.required = false;
    }


    /**
     * Constructs a newly allocated {@code Option} object with the given
     * attributes.
     *
     * @param name {@code Option} main name.
     * @param defaultValue {@code Option} default value.
     * @param description a description of the option.
     * @param type stored value class type.
     */
    public Option(String name, Object defaultValue, String description, Class type) {
        this.name = name;
        this.auxName = name;
        this.defaultValue = defaultValue;
        this.value = defaultValue;
        this.type = type;
        this.description = description;
        this.required = false;
    }


    /**
     * Constructs a newly allocated {@code Option} object with the given
     * attributes.
     *
     * @param name {@code Option} main name.
     * @param defaultValue {@code Option} default value.
     * @param type stored value class type.
     */
    public Option(String name, Object defaultValue, Class type) {
        this.name = name;
        this.auxName = name;
        this.defaultValue = defaultValue;
        this.value = defaultValue;
        this.type = type;
        this.description = "";
        this.required = false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (this.name == null) {
            this.auxName = name;
        }
        this.name = name;
    }

    public String getAuxName() {
        return auxName;
    }

    public void setAuxName(String auxName) {
        this.auxName = auxName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public Object getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(Object defaultValue) {
        this.defaultValue = defaultValue;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public Class getType() {
        return type;
    }

    public void setType(Class type) {
        this.type = type;
    }

    public boolean isStandalone() {
        return standalone;
    }

    public void setStandalone(boolean standalone) {
        this.standalone = standalone;
    }
    
    public boolean equals(String optName) {
        return this.auxName.equals(optName) || this.name.equals(optName);
    }
    
    public boolean hasAuxName(){
        return !name.equals(auxName);
    }
    
    public boolean isValueDefult(){
        return value.equals(defaultValue);
    }
    public void reset(){
        this.value=this.defaultValue;
    }
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 59 * hash + Objects.hashCode(this.name);
        hash = 59 * hash + Objects.hashCode(this.auxName);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final Option other = (Option) obj;
        if (!Objects.equals(this.name, other.name))
            return false;
        if (!Objects.equals(this.auxName, other.auxName))
            return false;
        return true;
    }

}
