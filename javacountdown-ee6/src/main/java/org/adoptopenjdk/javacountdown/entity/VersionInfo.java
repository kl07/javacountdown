/*
 * Copyright [2013] Adopt OpenJDK Programme
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.adoptopenjdk.javacountdown.entity;

import java.io.Serializable;

import com.google.code.morphia.annotations.Embedded;

/**
 * 
 * This entity represents the visitor's Java version information
 * 
 * @author Alex Theedom
 * 
 */
@Embedded
public class VersionInfo implements Serializable {
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + vBuild;
		result = prime * result + vMajor;
		result = prime * result + vMinor;
		result = prime * result + vPatch;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		VersionInfo other = (VersionInfo) obj;
		if (vBuild != other.vBuild)
			return false;
		if (vMajor != other.vMajor)
			return false;
		if (vMinor != other.vMinor)
			return false;
		if (vPatch != other.vPatch)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Visit{vMajor=" + vMajor + ", vMinor=" + vMinor + ", vPatch="
				+ vPatch + ", vBuild=" + vBuild + "}";
	}

}
