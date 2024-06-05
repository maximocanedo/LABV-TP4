package formatter;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;

import formatter.TextBlock.Alignment;

public class Formatter {
	
	private Object object;
	private Class<?> clazz;
	private Card card;
	private String displayClassName;
	private ITextBlock header = new TextBlock("");
	private String content;
	private ITextBlock footer = new TextBlock("···");
	private ArrayList<Method> fields = new ArrayList<>();
	private ArrayList<Method> fieldGetters = new ArrayList<>();

	public static Formatter of(Object obj) {
		Formatter f = new Formatter(obj);
		return f;
	}

	private Formatter(Object obj) {
		this.object = obj;
		this.setClazz(this.getObject().getClass());
		
		this.header.setAlignment(Alignment.RIGHT);
		this.header.setTopHeader(true);
		
		this.footer.setBottomHeader(true);
		this.footer.setAlignment(Alignment.CENTER);
		
		
	}
	
	public void lg(String x) {
		// System.out.println("[Formatter] " + x);
	}
	
	public int countFieldGetters(Class<?> clazz) {
		int i = 0;
		for(Method method : clazz.getMethods()) {
        	Format field = method.getDeclaredAnnotation(Format.class);
        	if(field == null || field.ignore()) continue;
        	i++;
        }
		return i;
	}
	
	private void updateFieldGetters() {
		ArrayList<Method> list = new ArrayList<>();
		for(Method method : this.getClazz().getMethods()) {
        	Format field = method.getDeclaredAnnotation(Format.class);
        	if(field == null || field.ignore()) continue;
        	list.add(method);
        }
		this.fieldGetters = list;
	}
	
	public void setClazz(Class<?> clazz) {
		this.clazz = clazz;
		this.setCard(this.getClazz().getDeclaredAnnotation(Card.class));
		this.updateFieldGetters();
		this.orderMethods();
		
	}
	
	public String toString() {
		String str = this.header.toString();
		str += this.content;
		str += this.footer.toString();
		return str;
	}
	
	public void orderMethods() {
		this.fields.clear();
		
        int fieldLength = this.fieldGetters.size();

        HashSet<Method> nonIndexedMethods = new HashSet<>();
        
        boolean stable = true;
        for(int i = 0; i < fieldLength; i++) {
        	lg("i = " + i);
        	boolean found = false;
        	for(Method m : this.getClazz().getMethods()) {
            	Format field = m.getDeclaredAnnotation(Format.class);
            	if(field == null || field.ignore()) continue;
            	if((!stable && (field.order() < 0 || field.order() >= i)) || field.order() < 0) {
            		nonIndexedMethods.add(m);
            		lg("Se agreg� " + m.getName() + "() a la lista del final." );
            	}
            	else if(field.order() == i) {
            		this.fields.add(i, m); 
            		lg("Se agreg� " + m.getName() + "() porque coincide i ("+i+") = order ("+field.order()+")" );
            		found = true;
            		break;
            	}
            }
        	if(!found) stable = false;
        }
        for(Method m : nonIndexedMethods) {
        	this.fields.add(m);
    		lg("Se agreg� " + m.getName() + "() al final" );
        }
        this.updateContent();
	}
	
	private void updateContent() {
		this.content = "";
		for(Method method : this.fields) {
        	Format field = method.getDeclaredAnnotation(Format.class);
        	if(field == null || field.ignore()) continue;
        	Object result = null;
            try {
                result = method.invoke(this.getObject());
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
                continue;
            }
            
            boolean returnsBooleanValue = method.getReturnType().getName() == "boolean";
            
            String resultString = result != null ? result.toString() : "null";
            if(returnsBooleanValue) {
            	boolean r = (boolean) result;
            	resultString = r ? field.whenTrue() : field.whenFalse();
            }
            
            String finalValue = field.prefix() + resultString + field.suffix();
            
            String cnt = (field.omitLabel() ? "" : field.label() + ": ") + finalValue;
            
        	ITextBlock line = new TextBlock(cnt);
        	line.setAlignment(field.align());
        	line.setLineSize(this.card.size());
        	this.content += line.toString();
        }
	}

	public Object getObject() {
		return object;
	}

	public Class<?> getClazz() {
		return clazz;
	}

	private void updateDisplayClassName() {
		if(this.object == null || this.clazz == null) {
			this.setDisplayClassName(null);
		}
		String simpleName = this.getClazz().getSimpleName();
		if(this.card == null || this.card.name() == null || this.card.name().isEmpty()) {
			lg("Simple name: " + simpleName);
			this.setDisplayClassName(simpleName);
		} else {
			lg("Card name: " + this.card.name());
			this.setDisplayClassName(this.card.name());
		}
	}
	
	private void setCard(Card card) {
		if(card == null) return;
		this.card = card;
		this.updateDisplayClassName();
		this.header.setLineSize(this.card.size());
		this.footer.setLineSize(this.card.size());
        
	}
	
	public String getDisplayClassName() {
		return displayClassName;
	}

	private void setDisplayClassName(String displayClassName) {
		this.displayClassName = "<" + (displayClassName == null ? "?" : displayClassName) + ">";
		this.header.setContent(this.displayClassName);
	}

	public ITextBlock getHeader() {
		return header;
	}

	public String getContent() {
		return content;
	}

	public ITextBlock getFooter() {
		return footer;
	}

}
