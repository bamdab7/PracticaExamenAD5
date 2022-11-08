
public class Libro {

	protected String isbn;
	protected String titulo;
	protected String editorial;
	protected int codigoAutor;
	
	public Libro() {
		super();
	}

	public Libro(String isbn, String titulo, String editorial, int codigoAutor) {
		super();
		this.isbn = isbn;
		this.titulo = titulo;
		this.editorial = editorial;
		this.codigoAutor = codigoAutor;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getEditorial() {
		return editorial;
	}

	public void setEditorial(String editorial) {
		this.editorial = editorial;
	}

	public int getCodigoAutor() {
		return codigoAutor;
	}

	public void setCodigoAutor(int codigoAutor) {
		this.codigoAutor = codigoAutor;
	}
	
	
}
