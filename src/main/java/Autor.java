
public class Autor {
	
	protected int codigo;
	protected String nombre;
	protected String pais;
	
	public Autor() {
		super();
	}

	public Autor(int codigo, String nombre, String pais) {
		super();
		this.codigo = codigo;
		this.nombre = nombre;
		this.pais = pais;
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}
	
	
}
