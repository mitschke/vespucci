/*
   Copyright 2011 Michael Eichberg et al

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */
package de.tud.cs.st.vespucci.sadclient.model;

/**
 * Mutable data-class modeling a Software-Architecture-Description.
 * 
 * @author Mateusz Parzonka
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
