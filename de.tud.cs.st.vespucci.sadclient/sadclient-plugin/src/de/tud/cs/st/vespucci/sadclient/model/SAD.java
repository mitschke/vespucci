/**
 *  License (BSD Style License):
 *   Copyright (c) 2012
 *   Software Engineering
 *   Department of Computer Science
 *   Technische Universität Darmstadt
 *   All rights reserved.
 * 
 *   Redistribution and use in source and binary forms, with or without
 *   modification, are permitted provided that the following conditions are met:
 * 
 *   - Redistributions of source code must retain the above copyright notice,
 *     this list of conditions and the following disclaimer.
 *   - Redistributions in binary form must reproduce the above copyright notice,
 *     this list of conditions and the following disclaimer in the documentation
 *     and/or other materials provided with the distribution.
 *   - Neither the name of the Software Engineering Group or Technische 
 *     Universität Darmstadt nor the names of its contributors may be used to 
 *     endorse or promote products derived from this software without specific 
 *     prior written permission.
 * 
 *   THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 *   AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 *   IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 *   ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 *   LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *   CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 *   SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 *   INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 *   CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 *   ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 *   POSSIBILITY OF SUCH DAMAGE.
 * 
 *
 * $Id$
 */
package de.tud.cs.st.vespucci.sadclient.model;

/**
 * Mutable data-class modeling a Software-Architecture-Description.
 * 
 * @author Mateusz Parzonka
 * 
 */
public class SAD {

    private String name;
    private String type;
    private String abstrct;
    private Model model;
    private Documentation documentation;
    private boolean wip;

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public Model getModel() {
	return model;
    }

    public void setModel(Model model) {
	this.model = model;
    }

    public Documentation getDocumentation() {
	return documentation;
    }

    public void setDocumentation(Documentation documentation) {
	this.documentation = documentation;
    }

    public String getType() {
	return type;
    }

    public void setType(String type) {
	this.type = type;
    }

    public String getAbstrct() {
	return abstrct;
    }

    public void setAbstrct(String abstrct) {
	this.abstrct = abstrct;
    }

    public boolean isWip() {
	return wip;
    }

    public void setWip(boolean wip) {
	this.wip = wip;
    }

    @Override
    public String toString() {
	StringBuilder builder = new StringBuilder();
	builder.append("SAD [name=");
	builder.append(name);
	builder.append(", type=");
	builder.append(type);
	builder.append(", abstrct=");
	builder.append(abstrct);
	builder.append(", model=");
	builder.append(model);
	builder.append(", documentation=");
	builder.append(documentation);
	builder.append(", wip=");
	builder.append(wip);
	builder.append("]");
	return builder.toString();
    }
    
    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((abstrct == null) ? 0 : abstrct.hashCode());
	result = prime * result + ((documentation == null) ? 0 : documentation.hashCode());
	result = prime * result + ((model == null) ? 0 : model.hashCode());
	result = prime * result + ((name == null) ? 0 : name.hashCode());
	result = prime * result + ((type == null) ? 0 : type.hashCode());
	result = prime * result + (wip ? 1231 : 1237);
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
	SAD other = (SAD) obj;
	if (abstrct == null) {
	    if (other.abstrct != null)
		return false;
	} else if (!abstrct.equals(other.abstrct))
	    return false;
	if (documentation == null) {
	    if (other.documentation != null)
		return false;
	} else if (!documentation.equals(other.documentation))
	    return false;
	if (model == null) {
	    if (other.model != null)
		return false;
	} else if (!model.equals(other.model))
	    return false;
	if (name == null) {
	    if (other.name != null)
		return false;
	} else if (!name.equals(other.name))
	    return false;
	if (type == null) {
	    if (other.type != null)
		return false;
	} else if (!type.equals(other.type))
	    return false;
	if (wip != other.wip)
	    return false;
	return true;
    }
    
    public static class Model {

	private int size;
	private String url;

	public int getSize() {
	    return size;
	}

	public void setSize(int size) {
	    this.size = size;
	}

	public String getUrl() {
	    return url;
	}

	public void setUrl(String url) {
	    this.url = url;
	}

	@Override
	public String toString() {
	    StringBuilder builder = new StringBuilder();
	    builder.append("Model [size=");
	    builder.append(size);
	    builder.append(", url=");
	    builder.append(url);
	    builder.append("]");
	    return builder.toString();
	}

	@Override
	public int hashCode() {
	    final int prime = 31;
	    int result = 1;
	    result = prime * result + size;
	    result = prime * result + ((url == null) ? 0 : url.hashCode());
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
	    Model other = (Model) obj;
	    if (size != other.size)
		return false;
	    if (url == null) {
		if (other.url != null)
		    return false;
	    } else if (!url.equals(other.url))
		return false;
	    return true;
	}

    }

    public static class Documentation {

	private int size;
	private String url;

	public int getSize() {
	    return size;
	}

	public void setSize(int size) {
	    this.size = size;
	}

	public String getUrl() {
	    return url;
	}

	public void setUrl(String url) {
	    this.url = url;
	}

	@Override
	public String toString() {
	    StringBuilder builder = new StringBuilder();
	    builder.append("Documentation [size=");
	    builder.append(size);
	    builder.append(", url=");
	    builder.append(url);
	    builder.append("]");
	    return builder.toString();
	}
	
	@Override
	public int hashCode() {
	    final int prime = 31;
	    int result = 1;
	    result = prime * result + size;
	    result = prime * result + ((url == null) ? 0 : url.hashCode());
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
	    Model other = (Model) obj;
	    if (size != other.size)
		return false;
	    if (url == null) {
		if (other.url != null)
		    return false;
	    } else if (!url.equals(other.url))
		return false;
	    return true;
	}

    }

}
