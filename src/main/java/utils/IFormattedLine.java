package utils;

import utils.FormattedLine.Alignment;
import utils.FormattedLine.Wrap;

public interface IFormattedLine {

	/**
	 * Devuelve las líneas generadas.
	 * 
	 * Dependiendo de la configuración elegida, las líneas pueden cortarse en saltos de línea, al final del content o de la última palabra antes del corte.
	 */
	String[] getLines();

	/**
	 * Devuelve el texto formateado, con los bordes, el margen y alineado.
	 */
	String toString();

	/**
	 * Devuelve la cantidad de líneas necesarias para mostrar el contenido.
	 */
	int getLinesUsed();

	/**
	 * Devuelve el texto en crudo sin formatear.
	 */
	String getContent();

	/**
	 * Establece el contenido a formatear.
	 * @param content El contenido a formatear.
	 */
	void setContent(String content);

	/**
	 * Devuelve el delimitador o el símbolo usado como borde izquierdo.
	 */
	String getLeftDelimiter();

	/**
	 * Establece el delimitador o el símbolo a usarse como borde izquierdo.
	 * @param leftDelimiter Delimitador izquierdo.
	 */
	void setLeftDelimiter(String leftDelimiter);

	/**
	 * Devuelve el delimitador o el símbolo usado como borde derecho.
	 */
	String getRightDelimiter();

	/**
	 * Establece el delimitador o el símbolo a usarse como borde derecho.
	 * @param rightDelimiter Delimitador derecho.
	 */
	void setRightDelimiter(String rightDelimiter);

	/**
	 * Devuelve la distancia entre los bordes horizontales y el texto de la línea.
	 */
	int getPadding();

	/**
	 * Establece la distancia entre los bordes horizontales y el texto de la línea.
	 * @param padding Distancia, en caracteres.
	 */
	void setPadding(int padding);

	/**
	 * Devuelve el tamaño máximo que puede ocupar el texto en una línea, sin contar el margen ni el borde.
	 */
	int getLineSize();

	/**
	 * Establece el tamaño máximo de caracteres que puede ocupar el texto en una línea, sin contar margen ni borde.
	 * @param lineSize Tamaño máximo de caracteres por línea.
	 */
	void setLineSize(int lineSize);

	/**
	 * Establece si el objeto será tratado o no como una cabecera inicial, con borde superior y título.
	 * @param isTopHeader Si el objeto es una cabecera inicial.
	 */
	void setTopHeader(boolean isTopHeader);

	/**
	 * Devuelve true si el objeto es una cabecera inicial.
	 */
	boolean isTopHeader();

	/**
	 * Establece si el objeto será tratado o no como una cabecera final o footer, con borde inferior y título.
	 * @param isBottomHeader Si el objeto es una cabecera final o footer.
	 */
	void setBottomHeader(boolean isBottomHeader);

	/**
	 * Devuelve true si el objeto es una cabecera final o footer.
	 */
	boolean isBottomHeader();

	/**
	 * Devuelve la configuración de alineamiento de texto.
	 */
	Alignment getAlignment();

	/**
	 * Alinea el texto de la línea.
	 * @param alignment Tipo de alineamiento.
	 */
	void setAlignment(Alignment alignment);

	/**
	 * Devuelve el caracter delimitador que será usado para bordes superiores.
	 */
	char getTopHeaderMiddleDelimiter();
	/**
	 * Devuelve el caracter delimitador que será usado para bordes inferiores.
	 */
	char getBottomHeaderMiddleDelimiter();

	/**
	 * Establece el delimitador a usarse en los bordes superior e inferior.
	 * @param del Delimitador.
	 */
	void setHeaderMiddleDelimiters(char del);

	/**
	 * Establece el delimitador a usarse en los bordes superior e inferior.
	 * @param topDelimiter Delimitador de borde superior
	 * @param bottomDelimiter Delimitador de borde inferior
	 */
	void setHeaderMiddleDelimiters(char topDelimiter, char bottomDelimiter);

	/**
	 * Devuelve los delimitadores usados en las esquinas.
	 * @return Delimitadores en formato de array [ Superior izquierdo, superior derecho, inferior izquierdo, inferior derecho ].
	 */
	char[] getCornerDelimiters();

	/**
	 * Establece los delimitadores a usarse en las esquinas.
	 * @param topLeftDelimiter Delimitador superior izquierdo.
	 * @param topRightDelimiter Delimitador superior derecho.
	 * @param bottomLeftDelimiter Delimitador inferior izquierdo.
	 * @param bottomRightDelimiter Delimitador inferior derecho.
	 */
	void setCornerDelimiters(char topLeftDelimiter, char topRightDelimiter, char bottomLeftDelimiter,
			char bottomRightDelimiter);

	/**
	 * Establece los delimitadores a usarse en las esquinas.
	 * @param delimiter Delimitador.
	 */
	void setCornerDelimiters(char delimiter);

	/**
	 * Establece los delimitadores a usarse en las esquinas.
	 * @param leftDelimiter Delimitador izquierdo.
	 * @param rightDelimiter Delimitador derecho.
	 */
	void setCornerDelimiters(char leftDelimiter, char rightDelimiter);

	/**
	 * Establece los delimitadores a usarse en las esquinas.
	 * @param topLeftDelimiter Delimitador superior izquierdo.
	 * @param topRightDelimiter Delimitador superior derecho.
	 * @param bottomDelimiter Delimitador inferior.
	 */
	void setCornerDelimiters(char topLeftDelimiter, char topRightDelimiter, char bottomDelimiter);

	/**
	 * Obtener configuración de corte de palabras o wrapping.
	 */
	Wrap getWrapping();
	
	/**
	 * Establece la configuración de wrapping.
	 * @param setting Configuración preferida.
	 */
	void setWrapping(Wrap setting);

	/**
	 * Devuelve true si el texto incluye una sangría al principio de la primer línea.
	 */
	boolean isIndented();

	/**
	 * Establece si se aplicará una sangría al principio de la primer línea de texto.
	 * @param indent Si incluye sangría.
	 */
	void indent(boolean indent);

}