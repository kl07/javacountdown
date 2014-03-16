package org.adoptopenjdk.javacountdown.entity;

import java.io.Serializable;




import javax.enterprise.context.RequestScoped;

import com.google.code.morphia.annotations.Embedded;

@Embedded
public class VersionInfo implements Serializable{
	private static final long serialVersionUID = 1L;
	
    private int vMajor;
    private int vMinor;
    private int vPatch;
    private int vBuild;
    
    
	public int getvMinor() {
		return vMinor;
	}
	
	public void setvMinor(int vMinor) {
		this.vMinor = vMinor;
	}
	
	public int getvMajor() {
		return vMajor;
	}
	
	public void setvMajor(int vMajor) {
		this.vMajor = vMajor;
	}
	
	public int getvPatch() {
		return vPatch;
	}
	
	public void setvPatch(int vPatch) {
		this.vPatch = vPatch;
	}
	
	public int getvBuild() {
		return vBuild;
	}
	
	public void setvBuild(int vBuild) {
		this.vBuild = vBuild;
	}
    
//    @Override
//    public int hashCode() {
//        int hash = 3;
//        hash = 79 * hash + (this.id != null ? this.id.hashCode() : 0);
//        return hash;
//    }
//
//    @Override
//    public boolean equals(Object obj) {
//        if (obj == null) {
//            return false;
//        }
//        if (getClass() != obj.getClass()) {
//            return false;
//        }
//        final Visit other = (Visit) obj;
//        return this.id == other.id || (this.id != null && this.id.equals(other.id));
//    }
//
//    @Override
//    public String toString() {
//        return "Visit{" + "version=" + version + ", vMajor=" + vMajor + ", vMinor=" + vMinor + ", vPatch=" + vPatch + ", vBuild=" + vBuild + ", latitude=" + latitude + ", longitude=" + longitude + ", country=" + country + ", time=" + time + ", id=" + id + '}';
//    }
    

}
