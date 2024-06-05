package formatter;

import java.util.ArrayList;
import java.util.List;

import formatter.ITextBlock;

public class TextBlock implements ITextBlock {
	/**
	 * Línea en blanco.
	 */
	public static ITextBlock BLANK = new TextBlock("");
	/**
	 * Tipos de alineamiento de texto disponibles
	 */
	public static enum Alignment {
		/**
		 * Alinear a la izquierda.
		 */
		LEFT, 
		/**
		 * Alinear al centro.
		 */
		CENTER, 
		/**
		 * Alinear a la derecha.
		 */
		RIGHT 
	};
	/**
	 * Tipos de wrapping disponibles.
	 */
	public static enum Wrap { 
		/**
		 * No cortar palabras.
		 * 
		 * Si una palabra no entra en una línea, se la omite y escribe en la siguiente.
		 */
		NO_WRAP, 
		/**
		 * Cortar palabras de ser necesario.
		 * 
		 * Si una palabra no entra, imprimir la parte de la palabra que quepe y el resto en la siguiente línea.
		 */
		WRAP_WORDS 
	};
	private String leftDelimiter = "|";
	private String rightDelimiter = "|";
	private int padding = 2;
	private int lineSize = 48;
	private String content;
	private boolean isTopHeader = false;
	private boolean isBottomHeader = false;
	private char topLeftDelimiter = '·';
	private char topRightDelimiter = '·';
	private char bottomLeftDelimiter = '·';
	private char bottomRightDelimiter = '·';
	private char topHeaderMiddleDelimiter = '—';
	private char bottomHeaderMiddleDelimiter = '—';
	private Alignment alignment = Alignment.LEFT;
	private Wrap wrap = Wrap.NO_WRAP;
	private boolean indent = false;
	

	public TextBlock(String content) {
		this.setContent(content);
	}
	
	/* (non-Javadoc)
	 * @see utils.IFormattedLine#getLines()
	 */
	
	public String[] getLines() {
		String content = this.getContent();
		if(this.indent) content = "   " + content; 
		int size = this.getLineSize();
	    List<String> lines = new ArrayList<String>();
		for(int i = 0; i < content.length(); i++) {
			int endIndex = Math.min(i + size, content.length());
			int newLineIndex = content.indexOf('\n', i);
			int nextIndex = ((newLineIndex != -1 && newLineIndex < endIndex) ? newLineIndex : endIndex);
			int endingIndex = (newLineIndex != -1 && newLineIndex < endIndex) ? newLineIndex : (endIndex - 1);
			lines.add(content.substring(i, nextIndex));
			i = endingIndex;
		}
		return lines.toArray(new String[0]);
	}
	
	/* (non-Javadoc)
	 * @see utils.IFormattedLine#toString()
	 */
	
	public String toString() {
		int padding = this.getPadding();
		int size = this.getLineSize();
		String res = "";
		int linesLength = this.getLinesUsed();
		char del = ' ';
		if(this.isTopHeader) del = topHeaderMiddleDelimiter;
		else if(this.isBottomHeader) del = bottomHeaderMiddleDelimiter;
		else del = ' ';
		String[] lines = this.getLines();
		
		char[] corners = this.getCornerDelimiters();
		
		for(int i = 0; i < linesLength; i++) {
			
			// Start delimiter
			if(i == 0 && this.isTopHeader) res += corners[0];
			else if( i == 0 && this.isBottomHeader) res += corners[2];
			else res += this.getLeftDelimiter();
			
			
			// Start padding
			for(int j = 0; j < this.getPadding(); j++) res += del;
			
			
			// Content
			String pagedContent = "";
			String rawContent = lines[i];
			if(this.getAlignment() != Alignment.CENTER) {
				if(this.getAlignment() == Alignment.LEFT) pagedContent += rawContent;
				if(rawContent.length() < size) {
					for(int k = rawContent.length(); k < size; k++) {
						pagedContent += del;
					}
				}
				if(this.getAlignment() == Alignment.RIGHT) pagedContent += rawContent;				
			}
			else {
				int availableSpace = size - rawContent.length();
				if(size > 0) {
					int leftPadding = (int) (Math.ceil(availableSpace / 2) / 1);
					int rightPadding = (int) (Math.floor(availableSpace / 2) / 1);
					if(availableSpace % 2 != 0) leftPadding++;
					for(int l = 0; l < leftPadding; l++) {
						pagedContent += del;
					}
					pagedContent += rawContent;
					for(int l = 0; l < rightPadding; l++) {
						pagedContent += del;
					}
				} else pagedContent += del;
			}
			res += pagedContent;
			
			// End padding
			for(int j = 0; j < padding; j++) res += del;
			
			// End delimiter
			if(i == 0 && this.isTopHeader) res += corners[1];
			else if(i == 0 && this.isBottomHeader) res += corners[3];
			else res += this.getRightDelimiter();
			
			// Line break
			res += "\n";
		}
		return res;
	}
	
	/* (non-Javadoc)
	 * @see utils.IFormattedLine#getLinesUsed()
	 */
	
	public int getLinesUsed() {
		return this.getLines().length;
	}
	
	/* (non-Javadoc)
	 * @see utils.IFormattedLine#getContent()
	 */
	
	public String getContent() {
		return this.content;
	}
	
	/* (non-Javadoc)
	 * @see utils.IFormattedLine#setContent(java.lang.String)
	 */
	
	public void setContent(String content) {
		this.content = content;
	}
	
	/* (non-Javadoc)
	 * @see utils.IFormattedLine#getLeftDelimiter()
	 */
	
	public String getLeftDelimiter() {
		return leftDelimiter;
	} 
	
	/* (non-Javadoc)
	 * @see utils.IFormattedLine#setLeftDelimiter(java.lang.String)
	 */
	
	public void setLeftDelimiter(String leftDelimiter) {
		this.leftDelimiter = leftDelimiter;
	}

	/* (non-Javadoc)
	 * @see utils.IFormattedLine#getRightDelimiter()
	 */
	
	public String getRightDelimiter() {
		return rightDelimiter;
	}

	/* (non-Javadoc)
	 * @see utils.IFormattedLine#setRightDelimiter(java.lang.String)
	 */
	
	public void setRightDelimiter(String rightDelimiter) {
		this.rightDelimiter = rightDelimiter;
	}

	/* (non-Javadoc)
	 * @see utils.IFormattedLine#getPadding()
	 */
	
	public int getPadding() {
		return padding;
	}

	/* (non-Javadoc)
	 * @see utils.IFormattedLine#setPadding(int)
	 */
	
	public void setPadding(int padding) {
		this.padding = padding;
	}

	/* (non-Javadoc)
	 * @see utils.IFormattedLine#getLineSize()
	 */
	
	public int getLineSize() {
		return lineSize;
	}

	/* (non-Javadoc)
	 * @see utils.IFormattedLine#setLineSize(int)
	 */
	
	public void setLineSize(int lineSize) {
		this.lineSize = lineSize;
	}

	/* (non-Javadoc)
	 * @see utils.IFormattedLine#setTopHeader(boolean)
	 */
	
	public void setTopHeader(boolean isTopHeader) {
		this.isTopHeader = isTopHeader;
		if(isTopHeader && this.isBottomHeader) this.isBottomHeader = false;
	}
	
	/* (non-Javadoc)
	 * @see utils.IFormattedLine#isTopHeader()
	 */
	
	public boolean isTopHeader() {
		return this.isTopHeader;
	}

	/* (non-Javadoc)
	 * @see utils.IFormattedLine#setBottomHeader(boolean)
	 */
	
	public void setBottomHeader(boolean isBottomHeader) {
		this.isBottomHeader = isBottomHeader;
		if(isBottomHeader && this.isTopHeader) this.isTopHeader = false;
	}
	
	/* (non-Javadoc)
	 * @see utils.IFormattedLine#isBottomHeader()
	 */
	
	public boolean isBottomHeader() {
		return this.isTopHeader;
	}
	
	/* (non-Javadoc)
	 * @see utils.IFormattedLine#getAlignment()
	 */
	
	public Alignment getAlignment() {
		return this.alignment;
	}
	
	/* (non-Javadoc)
	 * @see utils.IFormattedLine#setAlignment(utils.FormattedLine.Alignment)
	 */
	
	public void setAlignment(Alignment alignment) {
		this.alignment = alignment;
	}
	
	/* (non-Javadoc)
	 * @see utils.IFormattedLine#getTopHeaderMiddleDelimiter()
	 */
	
	public char getTopHeaderMiddleDelimiter() {
		return this.topHeaderMiddleDelimiter;
	}
	/* (non-Javadoc)
	 * @see utils.IFormattedLine#getBottomHeaderMiddleDelimiter()
	 */
	
	public char getBottomHeaderMiddleDelimiter() {
		return this.bottomHeaderMiddleDelimiter;
	}
	/* (non-Javadoc)
	 * @see utils.IFormattedLine#setHeaderMiddleDelimiters(char)
	 */
	
	public void setHeaderMiddleDelimiters(char del) {
		this.topHeaderMiddleDelimiter = del;
		this.bottomHeaderMiddleDelimiter = del;
	}
	/* (non-Javadoc)
	 * @see utils.IFormattedLine#setHeaderMiddleDelimiters(char, char)
	 */
	
	public void setHeaderMiddleDelimiters(char topDelimiter, char bottomDelimiter) {
		this.topHeaderMiddleDelimiter = topDelimiter;
		this.bottomHeaderMiddleDelimiter = bottomDelimiter;
	}
	
	/* (non-Javadoc)
	 * @see utils.IFormattedLine#getCornerDelimiters()
	 */
	
	public char[] getCornerDelimiters() {
		return new char[] {
			this.topLeftDelimiter,
			this.topRightDelimiter,
			this.bottomLeftDelimiter,
			this.bottomRightDelimiter
		};
	}
	
	/* (non-Javadoc)
	 * @see utils.IFormattedLine#setCornerDelimiters(char, char, char, char)
	 */
	
	public void setCornerDelimiters(char topLeftDelimiter, char topRightDelimiter, char bottomLeftDelimiter, char bottomRightDelimiter) {
		this.topLeftDelimiter = topLeftDelimiter;
		this.topRightDelimiter = topRightDelimiter;
		this.bottomLeftDelimiter = bottomLeftDelimiter;
		this.bottomRightDelimiter = bottomRightDelimiter;
	}

	/* (non-Javadoc)
	 * @see utils.IFormattedLine#setCornerDelimiters(char)
	 */
	
	public void setCornerDelimiters(char delimiter) {
		this.setCornerDelimiters(delimiter, delimiter, delimiter, delimiter);
	}
	/* (non-Javadoc)
	 * @see utils.IFormattedLine#setCornerDelimiters(char, char)
	 */
	
	public void setCornerDelimiters(char leftDelimiter, char rightDelimiter) {
		this.setCornerDelimiters(leftDelimiter, rightDelimiter, leftDelimiter, rightDelimiter);
	}
	/* (non-Javadoc)
	 * @see utils.IFormattedLine#setCornerDelimiters(char, char, char)
	 */
	
	public void setCornerDelimiters(char topLeftDelimiter, char topRightDelimiter, char bottomDelimiter) {
		this.setCornerDelimiters(topLeftDelimiter, topRightDelimiter, bottomDelimiter, bottomDelimiter);
	}
	
	/* (non-Javadoc)
	 * @see utils.IFormattedLine#getWrapping()
	 */
	
	public Wrap getWrapping() {
		return this.wrap;
	}
	/* (non-Javadoc)
	 * @see utils.IFormattedLine#setWrapping(utils.FormattedLine.Wrap)
	 */
	
	public void setWrapping(Wrap setting) {
		this.wrap = setting;
	}
	
	/* (non-Javadoc)
	 * @see utils.IFormattedLine#isIndented()
	 */
	
	public boolean isIndented() {
		return this.indent;
	}
	/* (non-Javadoc)
	 * @see utils.IFormattedLine#indent(boolean)
	 */
	
	public void indent(boolean indent) {
		this.indent = indent;
	}
	
	

}