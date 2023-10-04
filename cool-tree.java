// -*- mode: java -*-
//
// file: cool-tree.m4
//
// This file defines the AST
//
//////////////////////////////////////////////////////////

import java.util.Enumeration;
import java.io.PrintStream;
import java.util.Vector;


/** Defines simple phylum Program */
abstract class Program extends TreeNode {
    protected Program(int lineNumber) {
        super(lineNumber);
    }
    public abstract void dump_with_types(PrintStream out, int n);
    public abstract void semant();

}


/** Defines simple phylum Class_ */
abstract class Class_ extends TreeNode {
    protected Class_(int lineNumber) {
        super(lineNumber);
    }
    public abstract void dump_with_types(PrintStream out, int n);

}


/** Defines list phylum Classes
    <p>
    See <a href="ListNode.html">ListNode</a> for full documentation. */
class Classes extends ListNode {
    public final static Class elementClass = Class_.class;
    /** Returns class of this lists's elements */
    public Class getElementClass() {
        return elementClass;
    }
    protected Classes(int lineNumber, Vector elements) {
        super(lineNumber, elements);
    }
    /** Creates an empty "Classes" list */
    public Classes(int lineNumber) {
        super(lineNumber);
    }
    /** Appends "Class_" element to this list */
    public Classes appendElement(TreeNode elem) {
        addElement(elem);
        return this;
    }
    public TreeNode copy() {
        return new Classes(lineNumber, copyElements());
    }
}


/** Defines simple phylum Feature */
abstract class Feature extends TreeNode {
    protected Feature(int lineNumber) {
        super(lineNumber);
    }
    public abstract void dump_with_types(PrintStream out, int n);

	// metodos para definirlos en los hijos
    public abstract AbstractSymbol tipo();
    public abstract AbstractSymbol nombre();
	public abstract void semant(AbstractSymbol nombreClase, AbstractSymbol nombrePadre, ClassTable tablaClase);
}


/** Defines list phylum Features
    <p>
    See <a href="ListNode.html">ListNode</a> for full documentation. */
class Features extends ListNode {
    public final static Class elementClass = Feature.class;
    /** Returns class of this lists's elements */
    public Class getElementClass() {
        return elementClass;
    }
    protected Features(int lineNumber, Vector elements) {
        super(lineNumber, elements);
    }
    /** Creates an empty "Features" list */
    public Features(int lineNumber) {
        super(lineNumber);
    }
    /** Appends "Feature" element to this list */
    public Features appendElement(TreeNode elem) {
        addElement(elem);
        return this;
    }
    public TreeNode copy() {
        return new Features(lineNumber, copyElements());
    }
}


/** Defines simple phylum Formal */
abstract class Formal extends TreeNode {
    protected Formal(int lineNumber) {
        super(lineNumber);
    }
    public abstract void dump_with_types(PrintStream out, int n);

	// metodos para definirlos en los hijos
	public abstract AbstractSymbol nombre();
	public abstract AbstractSymbol tipo();
}


/** Defines list phylum Formals
    <p>
    See <a href="ListNode.html">ListNode</a> for full documentation. */
class Formals extends ListNode {
    public final static Class elementClass = Formal.class;
    /** Returns class of this lists's elements */
    public Class getElementClass() {
        return elementClass;
    }
    protected Formals(int lineNumber, Vector elements) {
        super(lineNumber, elements);
    }
    /** Creates an empty "Formals" list */
    public Formals(int lineNumber) {
        super(lineNumber);
    }
    /** Appends "Formal" element to this list */
    public Formals appendElement(TreeNode elem) {
        addElement(elem);
        return this;
    }
    public TreeNode copy() {
        return new Formals(lineNumber, copyElements());
    }
}


/** Defines simple phylum Expression */
abstract class Expression extends TreeNode {
    protected Expression(int lineNumber) {
        super(lineNumber);
    }
    private AbstractSymbol type = null;
    public AbstractSymbol get_type() { return type; }
    public Expression set_type(AbstractSymbol s) { type = s; return this; }
    public abstract void dump_with_types(PrintStream out, int n);
    public void dump_type(PrintStream out, int n) {
        if (type != null)
            { out.println(Utilities.pad(n) + ": " + type.getString()); }
        else
            { out.println(Utilities.pad(n) + ": _no_type"); }
    }
		// metodo para implementar en los hijos
		public abstract AbstractSymbol semant(AbstractSymbol nombreClase, ClassTable tablaClase);

}


/** Defines list phylum Expressions
    <p>
    See <a href="ListNode.html">ListNode</a> for full documentation. */
class Expressions extends ListNode {
    public final static Class elementClass = Expression.class;
    /** Returns class of this lists's elements */
    public Class getElementClass() {
        return elementClass;
    }
    protected Expressions(int lineNumber, Vector elements) {
        super(lineNumber, elements);
    }
    /** Creates an empty "Expressions" list */
    public Expressions(int lineNumber) {
        super(lineNumber);
    }
    /** Appends "Expression" element to this list */
    public Expressions appendElement(TreeNode elem) {
        addElement(elem);
        return this;
    }
    public TreeNode copy() {
        return new Expressions(lineNumber, copyElements());
    }
}


/** Defines simple phylum Case */
abstract class Case extends TreeNode {
    protected Case(int lineNumber) {
        super(lineNumber);
    }
    public abstract void dump_with_types(PrintStream out, int n);
	public abstract AbstractSymbol tipo();
	public abstract AbstractSymbol semant(AbstractSymbol nombreClase, ClassTable tablaClase);
}


/** Defines list phylum Cases
    <p>
    See <a href="ListNode.html">ListNode</a> for full documentation. */
class Cases extends ListNode {
    public final static Class elementClass = Case.class;
    /** Returns class of this lists's elements */
    public Class getElementClass() {
        return elementClass;
    }
    protected Cases(int lineNumber, Vector elements) {
        super(lineNumber, elements);
    }
    /** Creates an empty "Cases" list */
    public Cases(int lineNumber) {
        super(lineNumber);
    }
    /** Appends "Case" element to this list */
    public Cases appendElement(TreeNode elem) {
        addElement(elem);
        return this;
    }
    public TreeNode copy() {
        return new Cases(lineNumber, copyElements());
    }
}


/** Defines AST constructor 'programc'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class programc extends Program {
    protected Classes classes;
    /** Creates "programc" AST node.
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for classes
      */
    public programc(int lineNumber, Classes a1) {
        super(lineNumber);
        classes = a1;
    }
    public TreeNode copy() {
        return new programc(lineNumber, (Classes)classes.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "programc\n");
        classes.dump(out, n+2);
    }


    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_program");
        for (Enumeration e = classes.getElements(); e.hasMoreElements(); ) {
            // sm: changed 'n + 1' to 'n + 2' to match changes elsewhere
	    ((Class_)e.nextElement()).dump_with_types(out, n + 2);
        }
    }
    /** This method is the entry point to the semantic checker.  You will
        need to complete it in programming assignment 4.
	<p>
        Your checker should do the following two things:
	<ol>
	<li>Check that the program is semantically correct
	<li>Decorate the abstract syntax tree with type information
        by setting the type field in each Expression node.
        (see tree.h)
	</ol>
	<p>
	You are free to first do (1) and make sure you catch all semantic
    	errors. Part (2) can be done in a second stage when you want
	to test the complete compiler.
    */
    public void semant() {
		/* ClassTable constructor may do some semantic analysis */
		ClassTable classTable = new ClassTable(classes);

		/* some semantic analysis code may go here */

		// llenar tabla de simbolos
		for (Enumeration<class_c> clases = classes.getElements(); clases.hasMoreElements();)
		{
			class_c clase = (class_c)clases.nextElement();
			clase.llenarTablaSimbolos(classTable);
		}


		if(classTable.lookup(TreeConstants.Main) == null)
		{
			// si la clase main no esta definida
			SemantErrors.noClassMain(classTable.semantError());
		}
		else if((classTable.lookup(TreeConstants.Main)).metodo(TreeConstants.main_meth) == null)
		{
			//public static final AbstractSymbol Main = AbstractTable.idtable.addString("Main");
	    //public static final AbstractSymbol main_meth = AbstractTable.idtable.addString("main");
			// si el metodo main de la clase Main no esta definido
			SemantErrors.noMainMethodInMainClass(classTable.semantError());
		}
		else if(((classTable.lookup(TreeConstants.Main)).metodo(TreeConstants.main_meth)).formalsMetodo().getLength() != 0)
		{
			SemantErrors.mainMethodNoArgs(classTable.semantError());
		}

		// analizar la semantica de las demas clases
		for (Enumeration<class_c> clases = classes.getElements(); clases.hasMoreElements();)
		{
			class_c clase = (class_c)clases.nextElement();
			// llamar al analizador semantico de la clase
			clase.semant(classTable);
		}

		if (classTable.errors()) {
		    System.err.println("Compilation halted due to static semantic errors.");
		    System.exit(1);
		}
	}

}


/** Defines AST constructor 'class_c'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class class_c extends Class_ {
    protected AbstractSymbol name;
    protected AbstractSymbol parent;
    protected Features features;
    protected AbstractSymbol filename;

	// se agrega tabla de objetos y de metodos
	protected SymbolTable tablaObjeto;
	protected SymbolTable tablaMetodo;

    /** Creates "class_c" AST node.
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for name
      * @param a1 initial value for parent
      * @param a2 initial value for features
      * @param a3 initial value for filename
      */
    public class_c(int lineNumber, AbstractSymbol a1, AbstractSymbol a2, Features a3, AbstractSymbol a4) {
        super(lineNumber);
        name = a1;
        parent = a2;
        features = a3;
        filename = a4;
		// inicializar tablas y escope para cada una
		tablaObjeto = new SymbolTable();
		tablaObjeto.enterScope();
		tablaMetodo = new SymbolTable();
		tablaMetodo.enterScope();
}
    public TreeNode copy() {
        return new class_c(lineNumber, copy_AbstractSymbol(name), copy_AbstractSymbol(parent), (Features)features.copy(), copy_AbstractSymbol(filename));
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "class_c\n");
        dump_AbstractSymbol(out, n+2, name);
        dump_AbstractSymbol(out, n+2, parent);
        features.dump(out, n+2);
        dump_AbstractSymbol(out, n+2, filename);
    }


    public AbstractSymbol getFilename() { return filename; }
    public AbstractSymbol getName()     { return name; }
    public AbstractSymbol getParent()   { return parent; }

	// obtener metodo de la clase
	public method metodo(AbstractSymbol nombre)
	{
		return (method)tablaMetodo.lookup(nombre);
	}

	// obtener objeto de la clase
	public AbstractSymbol objeto(AbstractSymbol nombreObjeto)
	{
		return (AbstractSymbol)tablaObjeto.lookup(nombreObjeto);
	}

	// obtener la tabla de objetos
	public SymbolTable tablaObjeto()
	{
		return tablaObjeto;
	}

	// llenar tabla de simpbolos
	public void llenarTablaSimbolos(ClassTable tablaClase){
		for(Enumeration lstfeature = features.getElements(); lstfeature.hasMoreElements();)
		{
			Feature feature = (Feature)lstfeature.nextElement();
			// agregar el atributo self a la tabla
			tablaObjeto.addId(TreeConstants.self, TreeConstants.SELF_TYPE);

			// si el feature es un metodo
			if(feature instanceof method){
				//si no ha sido definido antes el metodo
				if(tablaMetodo.lookup(feature.nombre()) == null)
				{
					tablaMetodo.addId(feature.nombre(), feature);
				}
				else
				{
					SemantErrors.methodRedefined(feature.nombre(), name, tablaClase.semantError(filename, feature));
				}
			}
			// si el feature es un atributo
			if(feature instanceof attr)
			{
				//si no ha sido definido antes el atributo
				if((tablaObjeto.lookup(feature.nombre()) == null) && (feature.nombre() != TreeConstants.self))
				{
					tablaObjeto.addId(feature.nombre(), feature.tipo());
				}
				else
				{
					if (feature.nombre() != TreeConstants.self)
					{
						SemantErrors.attrRedefined(feature.nombre(), name, tablaClase.semantError(filename, feature));
					}
				}
			}
		}
	}

	// analizador semantico de la clase
	public void semant(ClassTable tablaClase){

        for(Enumeration lstfeature = features.getElements(); lstfeature.hasMoreElements();)
		{
			// llamar al analizador semantico de los features
			// son los que heredan de Feature: attr y method
			Feature feature = (Feature)lstfeature.nextElement();
			feature.semant(name, parent, tablaClase);
		}
	}

	public void dump_with_types(PrintStream out, int n) {
			dump_line(out, n);
			out.println(Utilities.pad(n) + "_class");
			dump_AbstractSymbol(out, n + 2, name);
			dump_AbstractSymbol(out, n + 2, parent);
			out.print(Utilities.pad(n + 2) + "\"");
			Utilities.printEscapedString(out, filename.getString());
			out.println("\"\n" + Utilities.pad(n + 2) + "(");
			for (Enumeration e = features.getElements(); e.hasMoreElements();) {
		((Feature)e.nextElement()).dump_with_types(out, n + 2);
			}
			out.println(Utilities.pad(n + 2) + ")");
	}
}


/** Defines AST constructor 'method'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class method extends Feature {
    protected AbstractSymbol name;
    protected Formals formals;
    protected AbstractSymbol return_type;
    protected Expression expr;
    /** Creates "method" AST node.
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for name
      * @param a1 initial value for formals
      * @param a2 initial value for return_type
      * @param a3 initial value for expr
      */
    public method(int lineNumber, AbstractSymbol a1, Formals a2, AbstractSymbol a3, Expression a4) {
        super(lineNumber);
        name = a1;
        formals = a2;
        return_type = a3;
        expr = a4;
    }
    public TreeNode copy() {
        return new method(lineNumber, copy_AbstractSymbol(name), (Formals)formals.copy(), copy_AbstractSymbol(return_type), (Expression)expr.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "method\n");
        dump_AbstractSymbol(out, n+2, name);
        formals.dump(out, n+2);
        dump_AbstractSymbol(out, n+2, return_type);
        expr.dump(out, n+2);
    }

	// obtener nombre del metodo
	public AbstractSymbol nombre()
	{
		return name;
	}
	// obtener tipo
	public AbstractSymbol tipo()
	{
		return return_type;
	}
	//obtener formals del metodo
	public Formals formalsMetodo()
	{
		return formals;
	}

	// analizador semantico del metodo
	public void semant(AbstractSymbol nombreClase, AbstractSymbol nombrePadre, ClassTable tablaClase)
	{
		// traer tabla de objetos
		class_c clase = (class_c)tablaClase.lookup(nombreClase);
		SymbolTable tablaObjeto = clase.tablaObjeto();

		// verificar si el metodo fue definido en un ancestro
		// entonces no se debe redefinir la firma
		method metodoAncestro = tablaClase.lookupMetodo(name, nombrePadre);
		// si es null es nuevo metodo, si no verificar
		if(metodoAncestro != null){
			Formals formalsAncestro = metodoAncestro.formalsMetodo();
			AbstractSymbol tipoAncestro = metodoAncestro.tipo();

            if (return_type != tipoAncestro){
                SemantErrors.methodRedefinedAncestor(name, return_type, tipoAncestro, tablaClase.semantError(((class_c)tablaClase.lookup(nombreClase)).getFilename(), this));
            }

			if (formals.getLength() != formalsAncestro.getLength())
			{
				// si el metodo heredado tiene diferente numero de formals parameters
				SemantErrors.methodArgsRedefinedAncestor(name, tablaClase.semantError(((class_c)tablaClase.lookup(nombreClase)).getFilename(), this));
			}
			else
			{
				// si tienen el mismo numero de formals entonces verificar el tipo
				Enumeration lstformalAncestro = formalsAncestro.getElements();
				for (Enumeration lstformal = formals.getElements(); lstformal.hasMoreElements();)
				{
					Formal formal = (Formal)lstformal.nextElement();
					Formal formalAncestro = (Formal)lstformalAncestro.nextElement();
					if(formal.tipo() != formalAncestro.tipo())
					{
    				    // Cuando un metodo es heredado y sus formals tienen diferente tipo al original
    				    SemantErrors.diffTypeFormalRedefinedMethod(formal.nombre(), name, formal.tipo(), formalAncestro.tipo(), tablaClase.semantError(((class_c)tablaClase.lookup(nombreClase)).getFilename(), this));
    				}
				}
			}
		}

		// crear scope para los formals
		tablaObjeto.enterScope();
		// verificar cada formal e insertarlo en la tabla
		for (Enumeration lstformals = formals.getElements(); lstformals.hasMoreElements();)
		{
			Formal formal = (Formal)lstformals.nextElement();

			if(formal.nombre() == TreeConstants.self){
				//Cuando declaran un formal con nombre self
				SemantErrors.selfCannotBeTheNameOfFormal(tablaClase.semantError(((class_c)tablaClase.lookup(nombreClase)).getFilename(), this));
			}
			else
			{
				// si no insertarlo en la tabla
				tablaObjeto.addId(formal.nombre(), formal.tipo());
			}

			// verificcar que el formal no tenga tipo SELF_TYPE
			if(formal.tipo() == TreeConstants.SELF_TYPE)
			{
				SemantErrors.formalParamCannotHaveTypeSELF_TYPE(name, tablaClase.semantError(((class_c)tablaClase.lookup(nombreClase)).getFilename(), this));
			}
            else if(!tablaClase.existeTipo(formal.tipo()))
            {
                SemantErrors.undefinedTypeOfFormal(formal.nombre(), formal.tipo() ,tablaClase.semantError(((class_c)tablaClase.lookup(nombreClase)).getFilename(), this));
            }
		}

        AbstractSymbol tipoExpr = expr.semant(nombreClase, tablaClase);
		// validar que el tipo de retorno ya este definido
		if(!tablaClase.existeTipo(return_type)){
			SemantErrors.undefinedReturnTypeMethod(return_type, tablaClase.semantError(((class_c)tablaClase.lookup(nombreClase)).getFilename(), this));
		}
        else if(!tablaClase.subTipo(tipoExpr, return_type, nombreClase))
        {
            SemantErrors.diffInitTypeMethod(tipoExpr, return_type, tablaClase.semantError(((class_c)tablaClase.lookup(nombreClase)).getFilename(), this));
        }

		tablaObjeto.exitScope();
	}

    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_method");
        dump_AbstractSymbol(out, n + 2, name);
        for (Enumeration e = formals.getElements(); e.hasMoreElements();) {
	    ((Formal)e.nextElement()).dump_with_types(out, n + 2);
        }
        dump_AbstractSymbol(out, n + 2, return_type);
	expr.dump_with_types(out, n + 2);
    }
}


/** Defines AST constructor 'attr'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class attr extends Feature {
    protected AbstractSymbol name;
    protected AbstractSymbol type_decl;
    protected Expression init;
    /** Creates "attr" AST node.
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for name
      * @param a1 initial value for type_decl
      * @param a2 initial value for init
      */
    public attr(int lineNumber, AbstractSymbol a1, AbstractSymbol a2, Expression a3) {
        super(lineNumber);
        name = a1;
        type_decl = a2;
        init = a3;
    }
    public TreeNode copy() {
        return new attr(lineNumber, copy_AbstractSymbol(name), copy_AbstractSymbol(type_decl), (Expression)init.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "attr\n");
        dump_AbstractSymbol(out, n+2, name);
        dump_AbstractSymbol(out, n+2, type_decl);
        init.dump(out, n+2);
    }


    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_attr");
        dump_AbstractSymbol(out, n + 2, name);
        dump_AbstractSymbol(out, n + 2, type_decl);
	init.dump_with_types(out, n + 2);
    }

	// obtener nombre del atributo
	public AbstractSymbol nombre()
	{
		return name;
	}

	// obtener el tipo del atributo
	public AbstractSymbol tipo()
	{
		return type_decl;
	}

	public void semant(AbstractSymbol nombreClase, AbstractSymbol nombrePadre, ClassTable tablaClase)
	{
        AbstractSymbol tipoExpr = init.semant(nombreClase, tablaClase);
		//System.out.println("llego aqui");
        if(name == TreeConstants.self){
			//verificar que no se declare el atributo como self
			SemantErrors.selfCannotBeTheNameOfAttr(tablaClase.semantError(((class_c)tablaClase.lookup(nombreClase)).getFilename(), this));
		}
		else if(tablaClase.lookupObjeto(name, nombrePadre) != TreeConstants.No_type)
		{
			// verificar que no se redefina un atributo
			SemantErrors.attrOfAnInheritedClass(name, tablaClase.semantError(((class_c)tablaClase.lookup(nombreClase)).getFilename(), this));
		}

		if(!tablaClase.existeTipo(type_decl))
		{
			// Cuando se declara un atributo con un tipo no definido
			SemantErrors.classOfAttrIsUndefined(type_decl, name, tablaClase.semantError(((class_c)tablaClase.lookup(nombreClase)).getFilename(), this));
		}
        else
        {
            if(tipoExpr != TreeConstants.No_type)
            {
                if(!tablaClase.subTipo(tipoExpr, type_decl, nombreClase))
                {
                    SemantErrors.diffInitTypeAttr(tipoExpr, type_decl, tablaClase.semantError(((class_c)tablaClase.lookup(nombreClase)).getFilename(), this));
                }
            }
        }

	}
}


/** Defines AST constructor 'formalc'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class formalc extends Formal {
    protected AbstractSymbol name;
    protected AbstractSymbol type_decl;
    /** Creates "formalc" AST node.
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for name
      * @param a1 initial value for type_decl
      */
    public formalc(int lineNumber, AbstractSymbol a1, AbstractSymbol a2) {
        super(lineNumber);
        name = a1;
        type_decl = a2;
    }
    public TreeNode copy() {
        return new formalc(lineNumber, copy_AbstractSymbol(name), copy_AbstractSymbol(type_decl));
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "formalc\n");
        dump_AbstractSymbol(out, n+2, name);
        dump_AbstractSymbol(out, n+2, type_decl);
    }


    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_formal");
        dump_AbstractSymbol(out, n + 2, name);
        dump_AbstractSymbol(out, n + 2, type_decl);
    }

	// obtener nombre del formal
	public AbstractSymbol nombre()
	{
		return name;
	}

	// obtener el tipo del formal
	public AbstractSymbol tipo()
	{
		return type_decl;
	}

}


/** Defines AST constructor 'branch'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class branch extends Case {
    protected AbstractSymbol name;
    protected AbstractSymbol type_decl;
    protected Expression expr;
    /** Creates "branch" AST node.
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for name
      * @param a1 initial value for type_decl
      * @param a2 initial value for expr
      */
    public branch(int lineNumber, AbstractSymbol a1, AbstractSymbol a2, Expression a3) {
        super(lineNumber);
        name = a1;
        type_decl = a2;
        expr = a3;
    }
    public TreeNode copy() {
        return new branch(lineNumber, copy_AbstractSymbol(name), copy_AbstractSymbol(type_decl), (Expression)expr.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "branch\n");
        dump_AbstractSymbol(out, n+2, name);
        dump_AbstractSymbol(out, n+2, type_decl);
        expr.dump(out, n+2);
    }

    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_branch");
        dump_AbstractSymbol(out, n + 2, name);
        dump_AbstractSymbol(out, n + 2, type_decl);
	expr.dump_with_types(out, n + 2);
    }

		public AbstractSymbol tipo() {
    	return type_decl;
    }

	public AbstractSymbol semant(AbstractSymbol nombreClase, ClassTable tablaClase)
	{
		SymbolTable tablaObjeto = (tablaClase.lookup(nombreClase)).tablaObjeto();
		// nuevo escope
		tablaObjeto.enterScope();

        if(!tablaClase.existeTipo(type_decl))
        {
            SemantErrors.undefinedTypeBranch(type_decl, name, tablaClase.semantError(((class_c)tablaClase.lookup(nombreClase)).getFilename(), this));
        }

        if(type_decl == TreeConstants.SELF_TYPE)
        {
            SemantErrors.selfTypeBranch(tablaClase.semantError(((class_c)tablaClase.lookup(nombreClase)).getFilename(), this));
            type_decl = TreeConstants.Object_;
        }

        if (name == TreeConstants.self)
        {
            SemantErrors.selfNameCase(tablaClase.semantError(((class_c)tablaClase.lookup(nombreClase)).getFilename(), this));
        } else
        {
            tablaObjeto.addId(name, type_decl);
        }

		

		// verificar semantica de expr
		AbstractSymbol tipoExpr = expr.semant(nombreClase, tablaClase);
		tablaObjeto.exitScope();
		return tipoExpr;
	}
}


/** Defines AST constructor 'assign'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class assign extends Expression {
    protected AbstractSymbol name;
    protected Expression expr;
    /** Creates "assign" AST node.
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for name
      * @param a1 initial value for expr
      */
    public assign(int lineNumber, AbstractSymbol a1, Expression a2) {
        super(lineNumber);
        name = a1;
        expr = a2;
    }
    public TreeNode copy() {
        return new assign(lineNumber, copy_AbstractSymbol(name), (Expression)expr.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "assign\n");
        dump_AbstractSymbol(out, n+2, name);
        expr.dump(out, n+2);
    }


    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_assign");
        dump_AbstractSymbol(out, n + 2, name);
	expr.dump_with_types(out, n + 2);
	dump_type(out, n);
    }

	public AbstractSymbol semant(AbstractSymbol nombreClase, ClassTable tablaClase)
	{
		// verificar el tipo de expr
		AbstractSymbol tipoExpr = expr.semant(nombreClase, tablaClase);

		// verificar que no se esta asignando a self
		if(name == TreeConstants.self)
		{
			SemantErrors.cannotAssignSelf(tablaClase.semantError(((class_c)tablaClase.lookup(nombreClase)).getFilename(), this));
		}

        AbstractSymbol tipo = tablaClase.lookupObjeto(name, nombreClase);
        if(tipo == TreeConstants.No_type)
        {
            SemantErrors.undeclaredIdentifiers(name, tablaClase.semantError(((class_c)tablaClase.lookup(nombreClase)).getFilename(), this));
            this.set_type(TreeConstants.Object_);
            return TreeConstants.Object_;
        }
        if(!tablaClase.existeTipo(tipo))
        {   
            SemantErrors.invalidTypeForAssign(tipo, name, tablaClase.semantError(((class_c)tablaClase.lookup(nombreClase)).getFilename(), this));
            this.set_type(TreeConstants.Object_);
            return TreeConstants.Object_;
        }
        if(!tablaClase.subTipo(tipoExpr, tipo, nombreClase))
        {
            SemantErrors.noSubtypeAssign(tipo, tipoExpr, tablaClase.semantError(((class_c)tablaClase.lookup(nombreClase)).getFilename(), this));
        }

		this.set_type(tipoExpr);
		return tipoExpr;
	}
}


/** Defines AST constructor 'static_dispatch'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class static_dispatch extends Expression {
    protected Expression expr;
    protected AbstractSymbol type_name;
    protected AbstractSymbol name;
    protected Expressions actual;
    /** Creates "static_dispatch" AST node.
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for expr
      * @param a1 initial value for type_name
      * @param a2 initial value for name
      * @param a3 initial value for actual
      */
    public static_dispatch(int lineNumber, Expression a1, AbstractSymbol a2, AbstractSymbol a3, Expressions a4) {
        super(lineNumber);
        expr = a1;
        type_name = a2;
        name = a3;
        actual = a4;
    }
    public TreeNode copy() {
        return new static_dispatch(lineNumber, (Expression)expr.copy(), copy_AbstractSymbol(type_name), copy_AbstractSymbol(name), (Expressions)actual.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "static_dispatch\n");
        expr.dump(out, n+2);
        dump_AbstractSymbol(out, n+2, type_name);
        dump_AbstractSymbol(out, n+2, name);
        actual.dump(out, n+2);
    }


    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_static_dispatch");
	expr.dump_with_types(out, n + 2);
        dump_AbstractSymbol(out, n + 2, type_name);
        dump_AbstractSymbol(out, n + 2, name);
        out.println(Utilities.pad(n + 2) + "(");
        for (Enumeration e = actual.getElements(); e.hasMoreElements();) {
	    ((Expression)e.nextElement()).dump_with_types(out, n + 2);
        }
        out.println(Utilities.pad(n + 2) + ")");
	dump_type(out, n);
    }

	public AbstractSymbol semant(AbstractSymbol nombreClase, ClassTable tablaClase)
	{
		method metodo;
		// verificar el tipo de expr
		AbstractSymbol tipoExpr = expr.semant(nombreClase, tablaClase);
		// para saber si hubo error
		boolean error = false;
		// validar si no hay error en estatic dispatch
		if(type_name == TreeConstants.SELF_TYPE)
		{    
            SemantErrors.dispatchFromSelfType(name, tablaClase.semantError(((class_c)tablaClase.lookup(nombreClase)).getFilename(), this));
			error = true;
		}
		else
		{
			if(!tablaClase.existeTipo(type_name))
			{
                SemantErrors.dispatchFromUndefined(name, type_name, tablaClase.semantError(((class_c)tablaClase.lookup(nombreClase)).getFilename(), this));
				error = true;
			}
			else
			{
				if(!tablaClase.subTipo(tipoExpr, type_name, nombreClase))
				{
					SemantErrors.dispatchNoSubtype(tipoExpr, type_name, tablaClase.semantError(((class_c)tablaClase.lookup(nombreClase)).getFilename(), this));
                    error = true;
				}
			}
		}

		Enumeration<Formal> formalsActual;
		if(error)
		{
			metodo = null;
			formalsActual = (new Formals(0)).getElements();
		}
		else
		{
			metodo = tablaClase.lookupMetodo(name, type_name);
			if(metodo == null)
			{
				// metodo no definido
                SemantErrors.methodUndefined(name, type_name, tablaClase.semantError(((class_c)tablaClase.lookup(nombreClase)).getFilename(), this));
				error = true;
				formalsActual = (new Formals(0)).getElements();
			}
			else
			{
				// metodo definido
				// verificar que los parametros de este metodo sean igual que
				// a los de la firma
				if(metodo.formalsMetodo().getLength() != actual.getLength())
				{
					// si no tienene el mismo numero de formals
					SemantErrors.methodInvokedWithWrongNumberOfArgs(name, type_name, metodo.formalsMetodo().getLength(), actual.getLength(), tablaClase.semantError(((class_c)tablaClase.lookup(nombreClase)).getFilename(), this));
                    error = true;
					formalsActual = (new Formals(0)).getElements();
				}
				else
				{
					// si tienen el mismo numero
					formalsActual = metodo.formalsMetodo().getElements();
				}
			}
		}

		// verificar tipos de los formals
		Enumeration<Expression> lstExpression = actual.getElements();
		while (lstExpression.hasMoreElements())
		{
			Formal formal;
			if(formalsActual.hasMoreElements())
			{
				formal = (Formal)formalsActual.nextElement();
			}
			else
			{
				formal = null;
			}
			Expression expression = (Expression)lstExpression.nextElement();
			AbstractSymbol tipoExpression = expression.semant(nombreClase, tablaClase);

			if((formal != null) && tablaClase.existeTipo(formal.tipo()))
			{
				if(!tablaClase.subTipo(tipoExpression, formal.tipo(), nombreClase))
				{  
                    SemantErrors.parameterDiffType(tipoExpression, formal.nombre(), formal.tipo(), tablaClase.semantError(((class_c)tablaClase.lookup(nombreClase)).getFilename(), this));
					error = true;
				}
			}
		}

		if(error){
			this.set_type(TreeConstants.Object_);
			return TreeConstants.Object_;
		}
		else if(metodo.tipo() == TreeConstants.SELF_TYPE)
		{
			this.set_type(tipoExpr);
			return tipoExpr;
		}
		else
		{
			this.set_type(metodo.tipo());
			return metodo.tipo();
		}

	}

}


/** Defines AST constructor 'dispatch'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class dispatch extends Expression {
    protected Expression expr;
    protected AbstractSymbol name;
    protected Expressions actual;
    /** Creates "dispatch" AST node.
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for expr
      * @param a1 initial value for name
      * @param a2 initial value for actual
      */
    public dispatch(int lineNumber, Expression a1, AbstractSymbol a2, Expressions a3) {
        super(lineNumber);
        expr = a1;
        name = a2;
        actual = a3;
    }
    public TreeNode copy() {
        return new dispatch(lineNumber, (Expression)expr.copy(), copy_AbstractSymbol(name), (Expressions)actual.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "dispatch\n");
        expr.dump(out, n+2);
        dump_AbstractSymbol(out, n+2, name);
        actual.dump(out, n+2);
    }


    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_dispatch");
	expr.dump_with_types(out, n + 2);
        dump_AbstractSymbol(out, n + 2, name);
        out.println(Utilities.pad(n + 2) + "(");
        for (Enumeration e = actual.getElements(); e.hasMoreElements();) {
	    ((Expression)e.nextElement()).dump_with_types(out, n + 2);
        }
        out.println(Utilities.pad(n + 2) + ")");
	dump_type(out, n);
    }

	public AbstractSymbol semant(AbstractSymbol nombreClase, ClassTable tablaClase)
	{
		boolean error = false;
		//verificar el tipo de expr
		AbstractSymbol tipoExpr = expr.semant(nombreClase, tablaClase);
		AbstractSymbol tipoBuscar;

		// si la clase es self entonces buscamos desde la clase actual
		if(tipoExpr == TreeConstants.SELF_TYPE){
			tipoBuscar = nombreClase;
		}
		else
		{
			tipoBuscar = tipoExpr;
		}

		method metodo = tablaClase.lookupMetodo(name, tipoBuscar);
		Enumeration<Formal> formalsActual;
		if(metodo == null)
		{
			// el metodo no esta definido
            SemantErrors.methodUndefined(name, tipoBuscar, tablaClase.semantError(((class_c)tablaClase.lookup(nombreClase)).getFilename(), this));
			error = true;
			formalsActual = (new Formals(0)).getElements();
		}
		else
		{
			// el metodo esta definido
			if(metodo.formalsMetodo().getLength() != actual.getLength())
			{
				SemantErrors.methodInvokedWithWrongNumberOfArgs(name, tipoBuscar, metodo.formalsMetodo().getLength(), actual.getLength(), tablaClase.semantError(((class_c)tablaClase.lookup(nombreClase)).getFilename(), this));
                error = true;
				formalsActual = (new Formals(0)).getElements();
			}
			else
			{
				formalsActual = metodo.formalsMetodo().getElements();
			}
		}

		Enumeration<Expression> lstExpression = actual.getElements();
		while(lstExpression.hasMoreElements())
		{
			Formal formal;
			if(formalsActual.hasMoreElements())
			{
				formal = (Formal)formalsActual.nextElement();
			}
			else
			{
				formal = null;
			}
			Expression expression = (Expression)lstExpression.nextElement();
			AbstractSymbol expressionTipo = expression.semant(nombreClase, tablaClase);

			if((formal != null) && tablaClase.existeTipo(formal.tipo()))
			{
				if(!tablaClase.subTipo(expressionTipo, formal.tipo(), nombreClase))
				{
                    SemantErrors.parameterDiffType(expressionTipo, formal.nombre(), formal.tipo(), tablaClase.semantError(((class_c)tablaClase.lookup(nombreClase)).getFilename(), this));
					error = true;
				}
			}
		}

		if (error)
		{
			this.set_type(TreeConstants.Object_);
			return TreeConstants.Object_;
		}
		else if (metodo.tipo() == TreeConstants.SELF_TYPE)
		{
			this.set_type(tipoExpr);
			return tipoExpr;
		}
		else
		{
			this.set_type(metodo.tipo());
			return metodo.tipo();
		}
	}
}


/** Defines AST constructor 'cond'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class cond extends Expression {
    protected Expression pred;
    protected Expression then_exp;
    protected Expression else_exp;
    /** Creates "cond" AST node.
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for pred
      * @param a1 initial value for then_exp
      * @param a2 initial value for else_exp
      */
    public cond(int lineNumber, Expression a1, Expression a2, Expression a3) {
        super(lineNumber);
        pred = a1;
        then_exp = a2;
        else_exp = a3;
    }
    public TreeNode copy() {
        return new cond(lineNumber, (Expression)pred.copy(), (Expression)then_exp.copy(), (Expression)else_exp.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "cond\n");
        pred.dump(out, n+2);
        then_exp.dump(out, n+2);
        else_exp.dump(out, n+2);
    }


    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_cond");
	pred.dump_with_types(out, n + 2);
	then_exp.dump_with_types(out, n + 2);
	else_exp.dump_with_types(out, n + 2);
	dump_type(out, n);
    }

	public AbstractSymbol semant(AbstractSymbol nombreClase, ClassTable tablaClase)
	{
		AbstractSymbol tipoPred = pred.semant(nombreClase, tablaClase);
		AbstractSymbol tipoThen = then_exp.semant(nombreClase, tablaClase);
		AbstractSymbol tipoElse = else_exp.semant(nombreClase, tablaClase);

		if(tipoPred != TreeConstants.Bool)
		{
			SemantErrors.ifNoBoolPredicate(tipoPred, tipoThen, tipoElse, tablaClase.semantError(((class_c)tablaClase.lookup(nombreClase)).getFilename(), this));
		}

		this.set_type(tablaClase.lub(tipoThen, tipoElse, nombreClase));
		return tablaClase.lub(tipoThen, tipoElse, nombreClase);
	}

}


/** Defines AST constructor 'loop'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class loop extends Expression {
    protected Expression pred;
    protected Expression body;
    /** Creates "loop" AST node.
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for pred
      * @param a1 initial value for body
      */
    public loop(int lineNumber, Expression a1, Expression a2) {
        super(lineNumber);
        pred = a1;
        body = a2;
    }
    public TreeNode copy() {
        return new loop(lineNumber, (Expression)pred.copy(), (Expression)body.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "loop\n");
        pred.dump(out, n+2);
        body.dump(out, n+2);
    }


    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_loop");
	pred.dump_with_types(out, n + 2);
	body.dump_with_types(out, n + 2);
	dump_type(out, n);
    }

	public AbstractSymbol semant(AbstractSymbol nombreClase, ClassTable tablaClase)
	{
		AbstractSymbol tipoPred = pred.semant(nombreClase, tablaClase);
		AbstractSymbol tipoBody = pred.semant(nombreClase, tablaClase);

		if(tipoPred != TreeConstants.Bool)
		{
			
            SemantErrors.whileNoBoolCondition(tipoPred, tipoBody, tablaClase.semantError(((class_c)tablaClase.lookup(nombreClase)).getFilename(), this));
		}

		this.set_type(TreeConstants.Object_);
		return TreeConstants.Object_;
	}
}


/** Defines AST constructor 'typcase'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class typcase extends Expression {
    protected Expression expr;
    protected Cases cases;
    /** Creates "typcase" AST node.
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for expr
      * @param a1 initial value for cases
      */
    public typcase(int lineNumber, Expression a1, Cases a2) {
        super(lineNumber);
        expr = a1;
        cases = a2;
    }
    public TreeNode copy() {
        return new typcase(lineNumber, (Expression)expr.copy(), (Cases)cases.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "typcase\n");
        expr.dump(out, n+2);
        cases.dump(out, n+2);
    }


    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_typcase");
	expr.dump_with_types(out, n + 2);
        for (Enumeration e = cases.getElements(); e.hasMoreElements();) {
	    ((Case)e.nextElement()).dump_with_types(out, n + 2);
        }
	dump_type(out, n);
    }

	public AbstractSymbol semant(AbstractSymbol nombreClase, ClassTable tablaClase)
	{
		AbstractSymbol tipoExpr = expr.semant(nombreClase, tablaClase);
		AbstractSymbol tipoRetorno = null;
		// verificar que no se dupliquen los casos del case
		AbstractSymbol expression = null;
		Vector tipos = new Vector();

		for(Enumeration<Case> lstExpression = cases.getElements(); lstExpression.hasMoreElements();)
		{
			Case caseExpr = (Case)lstExpression.nextElement();
			if(tipos.contains(caseExpr.tipo()))
			{
				SemantErrors.duplicateBranch(caseExpr.tipo(),tablaClase.semantError(((class_c)tablaClase.lookup(nombreClase)).getFilename(), this));
			}
			else
			{
				tipos.add(caseExpr.tipo());
			}

			if(tipoRetorno == null)
			{
				tipoRetorno = caseExpr.semant(nombreClase, tablaClase);
			}
			else
			{
				AbstractSymbol subTipo = caseExpr.semant(nombreClase, tablaClase);
				tipoRetorno = tablaClase.lub(tipoRetorno, subTipo, nombreClase);
			}
		}
		this.set_type(tipoRetorno);
		return tipoRetorno;
	}
}


/** Defines AST constructor 'block'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class block extends Expression {
    protected Expressions body;
    /** Creates "block" AST node.
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for body
      */
    public block(int lineNumber, Expressions a1) {
        super(lineNumber);
        body = a1;
    }
    public TreeNode copy() {
        return new block(lineNumber, (Expressions)body.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "block\n");
        body.dump(out, n+2);
    }


    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_block");
        for (Enumeration e = body.getElements(); e.hasMoreElements();) {
	    ((Expression)e.nextElement()).dump_with_types(out, n + 2);
        }
	dump_type(out, n);
    }

	public AbstractSymbol semant(AbstractSymbol nombreClase, ClassTable tablaClase)
	{
		AbstractSymbol tipoRetorno = null;
		for(Enumeration<Expression> bodyExpr = body.getElements(); bodyExpr.hasMoreElements();)
		{
			Expression expr = (Expression)bodyExpr.nextElement();
			tipoRetorno = expr.semant(nombreClase, tablaClase);
		}
		this.set_type(tipoRetorno);
		return tipoRetorno;
	}
}


/** Defines AST constructor 'let'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class let extends Expression {
    protected AbstractSymbol identifier;
    protected AbstractSymbol type_decl;
    protected Expression init;
    protected Expression body;
    /** Creates "let" AST node.
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for identifier
      * @param a1 initial value for type_decl
      * @param a2 initial value for init
      * @param a3 initial value for body
      */
    public let(int lineNumber, AbstractSymbol a1, AbstractSymbol a2, Expression a3, Expression a4) {
        super(lineNumber);
        identifier = a1;
        type_decl = a2;
        init = a3;
        body = a4;
    }
    public TreeNode copy() {
        return new let(lineNumber, copy_AbstractSymbol(identifier), copy_AbstractSymbol(type_decl), (Expression)init.copy(), (Expression)body.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "let\n");
        dump_AbstractSymbol(out, n+2, identifier);
        dump_AbstractSymbol(out, n+2, type_decl);
        init.dump(out, n+2);
        body.dump(out, n+2);
    }


    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_let");
	dump_AbstractSymbol(out, n + 2, identifier);
	dump_AbstractSymbol(out, n + 2, type_decl);
	init.dump_with_types(out, n + 2);
	body.dump_with_types(out, n + 2);
	dump_type(out, n);
    }

	public AbstractSymbol semant(AbstractSymbol nombreClase, ClassTable tablaClase)
	{
		class_c clase = tablaClase.lookup(nombreClase);
		SymbolTable tablaObjeto = clase.tablaObjeto();

		boolean error = false;
		AbstractSymbol tipoInit = init.semant(nombreClase, tablaClase);
		tablaObjeto.enterScope();

		if(!tablaClase.existeTipo(type_decl))
		{
			SemantErrors.letUndefinedType(type_decl, identifier, tablaClase.semantError(((class_c)tablaClase.lookup(nombreClase)).getFilename(), this));
			error = true;
		}
		else
		{
			if(tipoInit != TreeConstants.No_type)
			{
				if(!tablaClase.subTipo(tipoInit, type_decl, nombreClase))
				{
					SemantErrors.letDiffInitType(tipoInit, identifier, type_decl, tablaClase.semantError(((class_c)tablaClase.lookup(nombreClase)).getFilename(), this));
					error = true;
				}
			}
		}

		if (identifier == TreeConstants.self)
		{
			SemantErrors.selfCannotBeBoundInALet(tablaClase.semantError(((class_c)tablaClase.lookup(nombreClase)).getFilename(), this));
		}
		else
		{
			tablaObjeto.addId(identifier, type_decl);
		}

		AbstractSymbol tipoBody = body.semant(nombreClase, tablaClase);
		tablaObjeto.exitScope();

		this.set_type(tipoBody);
		return tipoBody;
	}
}


/** Defines AST constructor 'plus'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class plus extends Expression {
    protected Expression e1;
    protected Expression e2;
    /** Creates "plus" AST node.
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for e1
      * @param a1 initial value for e2
      */
    public plus(int lineNumber, Expression a1, Expression a2) {
        super(lineNumber);
        e1 = a1;
        e2 = a2;
    }
    public TreeNode copy() {
        return new plus(lineNumber, (Expression)e1.copy(), (Expression)e2.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "plus\n");
        e1.dump(out, n+2);
        e2.dump(out, n+2);
    }


    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_plus");
	e1.dump_with_types(out, n + 2);
	e2.dump_with_types(out, n + 2);
	dump_type(out, n);
    }

	public AbstractSymbol semant(AbstractSymbol nombreClase, ClassTable tablaClase)
	{
		AbstractSymbol tipoe1 = e1.semant(nombreClase, tablaClase);
		AbstractSymbol tipoe2 = e2.semant(nombreClase, tablaClase);

		if((tipoe1 != TreeConstants.Int) || (tipoe2 != TreeConstants.Int))
		{
			
            SemantErrors.noIntArguments(tipoe1, tipoe2, "+", tablaClase.semantError(((class_c)tablaClase.lookup(nombreClase)).getFilename(), this));
		}

		this.set_type(TreeConstants.Int);
		return TreeConstants.Int;
	}
}


/** Defines AST constructor 'sub'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class sub extends Expression {
    protected Expression e1;
    protected Expression e2;
    /** Creates "sub" AST node.
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for e1
      * @param a1 initial value for e2
      */
    public sub(int lineNumber, Expression a1, Expression a2) {
        super(lineNumber);
        e1 = a1;
        e2 = a2;
    }
    public TreeNode copy() {
        return new sub(lineNumber, (Expression)e1.copy(), (Expression)e2.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "sub\n");
        e1.dump(out, n+2);
        e2.dump(out, n+2);
    }


    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_sub");
	e1.dump_with_types(out, n + 2);
	e2.dump_with_types(out, n + 2);
	dump_type(out, n);
    }

	public AbstractSymbol semant(AbstractSymbol nombreClase, ClassTable tablaClase)
	{
		AbstractSymbol tipoe1 = e1.semant(nombreClase, tablaClase);
		AbstractSymbol tipoe2 = e2.semant(nombreClase, tablaClase);

		if((tipoe1 != TreeConstants.Int) || (tipoe2 != TreeConstants.Int))
		{
			SemantErrors.noIntArguments(tipoe1, tipoe2, "-", tablaClase.semantError(((class_c)tablaClase.lookup(nombreClase)).getFilename(), this));
		}

		this.set_type(TreeConstants.Int);
		return TreeConstants.Int;
	}
}


/** Defines AST constructor 'mul'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class mul extends Expression {
    protected Expression e1;
    protected Expression e2;
    /** Creates "mul" AST node.
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for e1
      * @param a1 initial value for e2
      */
    public mul(int lineNumber, Expression a1, Expression a2) {
        super(lineNumber);
        e1 = a1;
        e2 = a2;
    }
    public TreeNode copy() {
        return new mul(lineNumber, (Expression)e1.copy(), (Expression)e2.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "mul\n");
        e1.dump(out, n+2);
        e2.dump(out, n+2);
    }


    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_mul");
	e1.dump_with_types(out, n + 2);
	e2.dump_with_types(out, n + 2);
	dump_type(out, n);
    }

	public AbstractSymbol semant(AbstractSymbol nombreClase, ClassTable tablaClase)
	{
		AbstractSymbol tipoe1 = e1.semant(nombreClase, tablaClase);
		AbstractSymbol tipoe2 = e2.semant(nombreClase, tablaClase);

		if((tipoe1 != TreeConstants.Int) || (tipoe2 != TreeConstants.Int))
		{
			SemantErrors.noIntArguments(tipoe1, tipoe2, "*", tablaClase.semantError(((class_c)tablaClase.lookup(nombreClase)).getFilename(), this));
		}

		this.set_type(TreeConstants.Int);
		return TreeConstants.Int;
	}
}


/** Defines AST constructor 'divide'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class divide extends Expression {
    protected Expression e1;
    protected Expression e2;
    /** Creates "divide" AST node.
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for e1
      * @param a1 initial value for e2
      */
    public divide(int lineNumber, Expression a1, Expression a2) {
        super(lineNumber);
        e1 = a1;
        e2 = a2;
    }
    public TreeNode copy() {
        return new divide(lineNumber, (Expression)e1.copy(), (Expression)e2.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "divide\n");
        e1.dump(out, n+2);
        e2.dump(out, n+2);
    }


    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_divide");
	e1.dump_with_types(out, n + 2);
	e2.dump_with_types(out, n + 2);
	dump_type(out, n);
    }

	public AbstractSymbol semant(AbstractSymbol nombreClase, ClassTable tablaClase)
	{
		AbstractSymbol tipoe1 = e1.semant(nombreClase, tablaClase);
		AbstractSymbol tipoe2 = e2.semant(nombreClase, tablaClase);

		if((tipoe1 != TreeConstants.Int) || (tipoe2 != TreeConstants.Int))
		{
			SemantErrors.noIntArguments(tipoe1, tipoe2, "/", tablaClase.semantError(((class_c)tablaClase.lookup(nombreClase)).getFilename(), this));
		}

		this.set_type(TreeConstants.Int);
		return TreeConstants.Int;
	}
}


/** Defines AST constructor 'neg'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class neg extends Expression {
    protected Expression e1;
    /** Creates "neg" AST node.
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for e1
      */
    public neg(int lineNumber, Expression a1) {
        super(lineNumber);
        e1 = a1;
    }
    public TreeNode copy() {
        return new neg(lineNumber, (Expression)e1.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "neg\n");
        e1.dump(out, n+2);
    }


    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_neg");
	e1.dump_with_types(out, n + 2);
	dump_type(out, n);
    }

	public AbstractSymbol semant(AbstractSymbol nombreClase, ClassTable tablaClase)
	{
		AbstractSymbol tipoe1 = e1.semant(nombreClase, tablaClase);

		if((tipoe1 != TreeConstants.Int))
		{
			SemantErrors.argumentOfNegNoIntType(tipoe1, tablaClase.semantError(((class_c)tablaClase.lookup(nombreClase)).getFilename(), this));
		}

		this.set_type(TreeConstants.Int);
		return TreeConstants.Int;
	}
}


/** Defines AST constructor 'lt'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class lt extends Expression {
    protected Expression e1;
    protected Expression e2;
    /** Creates "lt" AST node.
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for e1
      * @param a1 initial value for e2
      */
    public lt(int lineNumber, Expression a1, Expression a2) {
        super(lineNumber);
        e1 = a1;
        e2 = a2;
    }
    public TreeNode copy() {
        return new lt(lineNumber, (Expression)e1.copy(), (Expression)e2.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "lt\n");
        e1.dump(out, n+2);
        e2.dump(out, n+2);
    }


    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_lt");
	e1.dump_with_types(out, n + 2);
	e2.dump_with_types(out, n + 2);
	dump_type(out, n);
    }

	public AbstractSymbol semant(AbstractSymbol nombreClase, ClassTable tablaClase)
	{
		AbstractSymbol tipoe1 = e1.semant(nombreClase, tablaClase);
		AbstractSymbol tipoe2 = e2.semant(nombreClase, tablaClase);

		if((tipoe1 != TreeConstants.Int) || (tipoe2 != TreeConstants.Int))
		{
			SemantErrors.noIntArguments(tipoe1, tipoe2, "<", tablaClase.semantError(((class_c)tablaClase.lookup(nombreClase)).getFilename(), this));
		}

		this.set_type(TreeConstants.Bool);
		return TreeConstants.Bool;
	}

}


/** Defines AST constructor 'eq'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class eq extends Expression {
    protected Expression e1;
    protected Expression e2;
    /** Creates "eq" AST node.
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for e1
      * @param a1 initial value for e2
      */
    public eq(int lineNumber, Expression a1, Expression a2) {
        super(lineNumber);
        e1 = a1;
        e2 = a2;
    }
    public TreeNode copy() {
        return new eq(lineNumber, (Expression)e1.copy(), (Expression)e2.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "eq\n");
        e1.dump(out, n+2);
        e2.dump(out, n+2);
    }


    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_eq");
	e1.dump_with_types(out, n + 2);
	e2.dump_with_types(out, n + 2);
	dump_type(out, n);
    }

	public AbstractSymbol semant(AbstractSymbol nombreClase, ClassTable tablaClase)
	{
		AbstractSymbol tipoe1 = e1.semant(nombreClase, tablaClase);
		AbstractSymbol tipoe2 = e2.semant(nombreClase, tablaClase);

		
        if ((tipoe1 == TreeConstants.Int) || (tipoe2 == TreeConstants.Int))
        {
            if (tipoe1 != tipoe2)
            {
                SemantErrors.illegalCompWithABasicTypeInt(tipoe1, tipoe2, tablaClase.semantError(((class_c)tablaClase.lookup(nombreClase)).getFilename(), this));
            }
        }

        if ((tipoe1 == TreeConstants.Bool) || (tipoe2 == TreeConstants.Bool))
        {
            if (tipoe1 != tipoe2) 
            {
                SemantErrors.illegalCompWithABasicTypeBool(tipoe1, tipoe2, tablaClase.semantError(((class_c)tablaClase.lookup(nombreClase)).getFilename(), this));
            }
        }

        if ((tipoe1 == TreeConstants.Str) || (tipoe2 == TreeConstants.Str))
        {
            if (tipoe1 != tipoe2) 
            {
                SemantErrors.illegalCompWithABasicTypeStr(tipoe1, tipoe2, tablaClase.semantError(((class_c)tablaClase.lookup(nombreClase)).getFilename(), this));
            }
        }

		this.set_type(TreeConstants.Bool);
		return TreeConstants.Bool;
	}

}


/** Defines AST constructor 'leq'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class leq extends Expression {
    protected Expression e1;
    protected Expression e2;
    /** Creates "leq" AST node.
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for e1
      * @param a1 initial value for e2
      */
    public leq(int lineNumber, Expression a1, Expression a2) {
        super(lineNumber);
        e1 = a1;
        e2 = a2;
    }
    public TreeNode copy() {
        return new leq(lineNumber, (Expression)e1.copy(), (Expression)e2.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "leq\n");
        e1.dump(out, n+2);
        e2.dump(out, n+2);
    }


    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_leq");
	e1.dump_with_types(out, n + 2);
	e2.dump_with_types(out, n + 2);
	dump_type(out, n);
    }

	public AbstractSymbol semant(AbstractSymbol nombreClase, ClassTable tablaClase)
	{
		AbstractSymbol tipoe1 = e1.semant(nombreClase, tablaClase);
		AbstractSymbol tipoe2 = e2.semant(nombreClase, tablaClase);

		if((tipoe1 != TreeConstants.Int) || (tipoe2 != TreeConstants.Int))
		{
			SemantErrors.noIntArguments(tipoe1, tipoe2, "<=", tablaClase.semantError(((class_c)tablaClase.lookup(nombreClase)).getFilename(), this));
		}

		this.set_type(TreeConstants.Bool);
		return TreeConstants.Bool;
	}
}


/** Defines AST constructor 'comp'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class comp extends Expression {
    protected Expression e1;
    /** Creates "comp" AST node.
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for e1
      */
    public comp(int lineNumber, Expression a1) {
        super(lineNumber);
        e1 = a1;
    }
    public TreeNode copy() {
        return new comp(lineNumber, (Expression)e1.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "comp\n");
        e1.dump(out, n+2);
    }


    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_comp");
	e1.dump_with_types(out, n + 2);
	dump_type(out, n);
    }

	public AbstractSymbol semant(AbstractSymbol nombreClase, ClassTable tablaClase)
	{
		AbstractSymbol tipoe1 = e1.semant(nombreClase, tablaClase);

		if((tipoe1 != TreeConstants.Bool))
		{
			SemantErrors.notNotBoolType(tipoe1, tablaClase.semantError(((class_c)tablaClase.lookup(nombreClase)).getFilename(), this));
		}

		this.set_type(TreeConstants.Bool);
		return TreeConstants.Bool;
	}

}


/** Defines AST constructor 'int_const'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class int_const extends Expression {
    protected AbstractSymbol token;
    /** Creates "int_const" AST node.
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for token
      */
    public int_const(int lineNumber, AbstractSymbol a1) {
        super(lineNumber);
        token = a1;
    }
    public TreeNode copy() {
        return new int_const(lineNumber, copy_AbstractSymbol(token));
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "int_const\n");
        dump_AbstractSymbol(out, n+2, token);
    }


    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_int");
	dump_AbstractSymbol(out, n + 2, token);
	dump_type(out, n);
    }

	public AbstractSymbol semant(AbstractSymbol nombreClase, ClassTable tablaClase)
	{
		this.set_type(TreeConstants.Int);
		return TreeConstants.Int;
	}
}


/** Defines AST constructor 'bool_const'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class bool_const extends Expression {
    protected Boolean val;
    /** Creates "bool_const" AST node.
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for val
      */
    public bool_const(int lineNumber, Boolean a1) {
        super(lineNumber);
        val = a1;
    }
    public TreeNode copy() {
        return new bool_const(lineNumber, copy_Boolean(val));
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "bool_const\n");
        dump_Boolean(out, n+2, val);
    }


    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_bool");
	dump_Boolean(out, n + 2, val);
	dump_type(out, n);
    }

	public AbstractSymbol semant(AbstractSymbol nombreClase, ClassTable tablaClase)
	{
		this.set_type(TreeConstants.Bool);
		return TreeConstants.Bool;
	}
}


/** Defines AST constructor 'string_const'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class string_const extends Expression {
    protected AbstractSymbol token;
    /** Creates "string_const" AST node.
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for token
      */
    public string_const(int lineNumber, AbstractSymbol a1) {
        super(lineNumber);
        token = a1;
    }
    public TreeNode copy() {
        return new string_const(lineNumber, copy_AbstractSymbol(token));
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "string_const\n");
        dump_AbstractSymbol(out, n+2, token);
    }


    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_string");
	out.print(Utilities.pad(n + 2) + "\"");
	Utilities.printEscapedString(out, token.getString());
	out.println("\"");
	dump_type(out, n);
    }

	public AbstractSymbol semant(AbstractSymbol nombreClase, ClassTable tablaClase)
	{
		this.set_type(TreeConstants.Str);
		return TreeConstants.Str;
	}
}


/** Defines AST constructor 'new_'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class new_ extends Expression {
    protected AbstractSymbol type_name;
    /** Creates "new_" AST node.
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for type_name
      */
    public new_(int lineNumber, AbstractSymbol a1) {
        super(lineNumber);
        type_name = a1;
    }
    public TreeNode copy() {
        return new new_(lineNumber, copy_AbstractSymbol(type_name));
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "new_\n");
        dump_AbstractSymbol(out, n+2, type_name);
    }


    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_new");
	dump_AbstractSymbol(out, n + 2, type_name);
	dump_type(out, n);
    }

	public AbstractSymbol semant(AbstractSymbol nombreClase, ClassTable tablaClase)
	{
		if(tablaClase.existeTipo(type_name))
		{
			this.set_type(type_name);
			return type_name;
		}
		else
		{
			SemantErrors.newUndefinedClass(type_name, tablaClase.semantError(((class_c)tablaClase.lookup(nombreClase)).getFilename(), this));
			this.set_type(TreeConstants.Object_);
			return TreeConstants.Object_;
		}
	}
}


/** Defines AST constructor 'isvoid'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class isvoid extends Expression {
    protected Expression e1;
    /** Creates "isvoid" AST node.
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for e1
      */
    public isvoid(int lineNumber, Expression a1) {
        super(lineNumber);
        e1 = a1;
    }
    public TreeNode copy() {
        return new isvoid(lineNumber, (Expression)e1.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "isvoid\n");
        e1.dump(out, n+2);
    }


    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_isvoid");
	e1.dump_with_types(out, n + 2);
	dump_type(out, n);
    }

		public AbstractSymbol semant(AbstractSymbol nombreClase, ClassTable tablaClase)
		{
			e1.semant(nombreClase, tablaClase);
			this.set_type(TreeConstants.Bool);
			return TreeConstants.Bool;
		}
}


/** Defines AST constructor 'no_expr'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class no_expr extends Expression {
    /** Creates "no_expr" AST node.
      *
      * @param lineNumber the line in the source file from which this node came.
      */
    public no_expr(int lineNumber) {
        super(lineNumber);
    }
    public TreeNode copy() {
        return new no_expr(lineNumber);
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "no_expr\n");
    }


    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_no_expr");
	dump_type(out, n);
    }

	public AbstractSymbol semant(AbstractSymbol nombreClase, ClassTable tablaClase)
	{
		this.set_type(TreeConstants.No_type);
		return TreeConstants.No_type;
	}
}


/** Defines AST constructor 'object'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class object extends Expression {
    protected AbstractSymbol name;
    /** Creates "object" AST node.
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for name
      */
    public object(int lineNumber, AbstractSymbol a1) {
        super(lineNumber);
        name = a1;
    }
    public TreeNode copy() {
        return new object(lineNumber, copy_AbstractSymbol(name));
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "object\n");
        dump_AbstractSymbol(out, n+2, name);
    }


    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_object");
	dump_AbstractSymbol(out, n + 2, name);
	dump_type(out, n);
    }

	public AbstractSymbol semant(AbstractSymbol nombreClase, ClassTable tablaClase)
	{
		AbstractSymbol tipo = tablaClase.lookupObjeto(name, nombreClase);
		// si el tipo no esta definido
		if(tipo == TreeConstants.No_type)
		{
			SemantErrors.undeclaredIdentifiers(name, tablaClase.semantError(((class_c)tablaClase.lookup(nombreClase)).getFilename(), this));
			return TreeConstants.Object_;
		}

		if(!tablaClase.existeTipo(tipo))
		{
			return TreeConstants.Object_;
		}
		this.set_type(tipo);
		return tipo;
	}
}
