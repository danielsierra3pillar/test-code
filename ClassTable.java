import java.io.PrintStream;
import java.util.Enumeration;
/** This class may be used to contain the semantic information such as
 * the inheritance graph.  You may use it or not as you like: it is only
 * here to provide a container for the supplied methods.  */
class ClassTable {
    private int semantErrors;
    private PrintStream errorStream;
	private SymbolTable tablaClase;

    /** Creates data structures representing basic Cool classes (Object,
     * IO, Int, Bool, String).  Please note: as is this method does not
     * do anything useful; you will need to edit it to make if do what
     * you want.
     * */
    private void installBasicClasses() {
	AbstractSymbol filename
	    = AbstractTable.stringtable.addString("<basic class>");

	// The following demonstrates how to create dummy parse trees to
	// refer to basic Cool classes.  There's no need for method
	// bodies -- these are already built into the runtime system.

	// IMPORTANT: The results of the following expressions are
	// stored in local variables.  You will want to do something
	// with those variables at the end of this method to make this
	// code meaningful.

	// The Object class has no parent class. Its methods are
	//        cool_abort() : Object    aborts the program
	//        type_name() : Str        returns a string representation
	//                                 of class name
	//        copy() : SELF_TYPE       returns a copy of the object

	class_c Object_class =
	    new class_c(0,
		       TreeConstants.Object_,
		       TreeConstants.No_class,
		       new Features(0)
			   .appendElement(new method(0,
					      TreeConstants.cool_abort,
					      new Formals(0),
					      TreeConstants.Object_,
					      new no_expr(0)))
			   .appendElement(new method(0,
					      TreeConstants.type_name,
					      new Formals(0),
					      TreeConstants.Str,
					      new no_expr(0)))
			   .appendElement(new method(0,
					      TreeConstants.copy,
					      new Formals(0),
					      TreeConstants.SELF_TYPE,
					      new no_expr(0))),
		       filename);

	// The IO class inherits from Object. Its methods are
	//        out_string(Str) : SELF_TYPE  writes a string to the output
	//        out_int(Int) : SELF_TYPE      "    an int    "  "     "
	//        in_string() : Str            reads a string from the input
	//        in_int() : Int                "   an int     "  "     "

	class_c IO_class =
	    new class_c(0,
		       TreeConstants.IO,
		       TreeConstants.Object_,
		       new Features(0)
			   .appendElement(new method(0,
					      TreeConstants.out_string,
					      new Formals(0)
						  .appendElement(new formalc(0,
								     TreeConstants.arg,
								     TreeConstants.Str)),
					      TreeConstants.SELF_TYPE,
					      new no_expr(0)))
			   .appendElement(new method(0,
					      TreeConstants.out_int,
					      new Formals(0)
						  .appendElement(new formalc(0,
								     TreeConstants.arg,
								     TreeConstants.Int)),
					      TreeConstants.SELF_TYPE,
					      new no_expr(0)))
			   .appendElement(new method(0,
					      TreeConstants.in_string,
					      new Formals(0),
					      TreeConstants.Str,
					      new no_expr(0)))
			   .appendElement(new method(0,
					      TreeConstants.in_int,
					      new Formals(0),
					      TreeConstants.Int,
					      new no_expr(0))),
		       filename);

	// The Int class has no methods and only a single attribute, the
	// "val" for the integer.

	class_c Int_class =
	    new class_c(0,
		       TreeConstants.Int,
		       TreeConstants.Object_,
		       new Features(0)
			   .appendElement(new attr(0,
					    TreeConstants.val,
					    TreeConstants.prim_slot,
					    new no_expr(0))),
		       filename);

	// Bool also has only the "val" slot.
	class_c Bool_class =
	    new class_c(0,
		       TreeConstants.Bool,
		       TreeConstants.Object_,
		       new Features(0)
			   .appendElement(new attr(0,
					    TreeConstants.val,
					    TreeConstants.prim_slot,
					    new no_expr(0))),
		       filename);

	// The class Str has a number of slots and operations:
	//       val                              the length of the string
	//       str_field                        the string itself
	//       length() : Int                   returns length of the string
	//       concat(arg: Str) : Str           performs string concatenation
	//       substr(arg: Int, arg2: Int): Str substring selection

	class_c Str_class =
	    new class_c(0,
		       TreeConstants.Str,
		       TreeConstants.Object_,
		       new Features(0)
			   .appendElement(new attr(0,
					    TreeConstants.val,
					    TreeConstants.Int,
					    new no_expr(0)))
			   .appendElement(new attr(0,
					    TreeConstants.str_field,
					    TreeConstants.prim_slot,
					    new no_expr(0)))
			   .appendElement(new method(0,
					      TreeConstants.length,
					      new Formals(0),
					      TreeConstants.Int,
					      new no_expr(0)))
			   .appendElement(new method(0,
					      TreeConstants.concat,
					      new Formals(0)
						  .appendElement(new formalc(0,
								     TreeConstants.arg,
								     TreeConstants.Str)),
					      TreeConstants.Str,
					      new no_expr(0)))
			   .appendElement(new method(0,
					      TreeConstants.substr,
					      new Formals(0)
						  .appendElement(new formalc(0,
								     TreeConstants.arg,
								     TreeConstants.Int))
						  .appendElement(new formalc(0,
								     TreeConstants.arg2,
								     TreeConstants.Int)),
					      TreeConstants.Str,
					      new no_expr(0))),
		       filename);

	/* Do somethind with Object_class, IO_class, Int_class,
           Bool_class, and Str_class here */

		//agregar a la tabla de clases
		tablaClase.addId(TreeConstants.Object_, Object_class);
		tablaClase.addId(TreeConstants.IO, IO_class);
		tablaClase.addId(TreeConstants.Int, Int_class);
		tablaClase.addId(TreeConstants.Bool, Bool_class);
		tablaClase.addId(TreeConstants.Str, Str_class);

		//llenar su tabla de simbolos
		Object_class.llenarTablaSimbolos(this);
		IO_class.llenarTablaSimbolos(this);
		Int_class.llenarTablaSimbolos(this);
		Bool_class.llenarTablaSimbolos(this);
		Str_class.llenarTablaSimbolos(this);
    }



    public ClassTable(Classes cls) {
		semantErrors = 0;
		errorStream = System.err;
		/* fill this in */

		// inicializa la tabla de clases
		tablaClase = new SymbolTable();
		// inicializa un scope
		tablaClase.enterScope();
		// se agregan primero las clases basicas
		installBasicClasses();

		// se recorren las clases para verificar que no se redfinan
		// no hereden de una clase basica o se intente sobreescribir
		// tambien se valida la herencia
		for(Enumeration<class_c> clases = cls.getElements(); clases.hasMoreElements();)
		{
			class_c clase = clases.nextElement();
			//System.out.println(clase.getName());

			/* se valida que no se redefinan una clase basica */
			if((clase.getName() == TreeConstants.Object_) || (clase.getName() == TreeConstants.IO) ||
			   (clase.getName() == TreeConstants.Int) || (clase.getName() == TreeConstants.Str) ||
			   (clase.getName() == TreeConstants.Bool) || (clase.getName() == TreeConstants.SELF_TYPE)
			)
		 	{
			 	SemantErrors.basicClassRedefined(clase.getName(), semantError(clase));
			 	System.exit(1);
		 	}

			/* verificar que no herede de clase basica */
			if((clase.getParent() == TreeConstants.Int) || (clase.getParent() == TreeConstants.Bool) ||
			   (clase.getParent() == TreeConstants.Str) || (clase.getParent() == TreeConstants.SELF_TYPE)
			)
			{
				SemantErrors.cannotInheritClass(clase.getName(), clase.getParent(), semantError(clase));
				System.exit(1);
			}

			// verificar que no se haya definido antes la clase mediante
			// el metodo lookup de la tabla de simbolos
			if((tablaClase.lookup(clase.getName()) != null))
			{
				SemantErrors.classPreviouslyDefined(clase.getName(), semantError(clase));
				System.exit(1);
			}
			// si no ha sido definida antes entonces la agregamos a la tabla
			tablaClase.addId(clase.getName(), clase);

			// verificar que no existe ciclos en la herencia
			if(verificaHerencia(clase.getName()))
			{
				SemantErrors.inheritanceCycle(clase.getName(), semantError(clase));
				System.exit(1);
			}
		}

		// una vez que se lleno la tablaClase entonces se valida que no exista
		// herencia de una clase no definida
		for(Enumeration<class_c> clases = cls.getElements(); clases.hasMoreElements();)
		{
			class_c clase = clases.nextElement();
			Boolean heredaObject = false;
			while(clase != null)
			{
				AbstractSymbol nombrePadre = clase.getParent();
				if(nombrePadre == TreeConstants.Object_)
				{
					heredaObject = true;
					return;
				}
				clase = (class_c)tablaClase.lookup(nombrePadre);
			}
			// si no llega a object entonces quiere decir que en algun
			// punto uno de los ancestros de la clase es indefinido
			if(!heredaObject)
			{
				SemantErrors.inheritsFromAnUndefinedClass(clase.getName(), clase.getParent(), semantError(clase));
				System.exit(1);
			}
		}
    }

    /** Prints line number and file name of the given class.
     *
     * Also increments semantic error count.
     *
     * @param c the class
     * @return a print stream to which the rest of the error message is
     * to be printed.
     *
     * */
    public PrintStream semantError(class_c c) {
	return semantError(c.getFilename(), c);
    }

    /** Prints the file name and the line number of the given tree node.
     *
     * Also increments semantic error count.
     *
     * @param filename the file name
     * @param t the tree node
     * @return a print stream to which the rest of the error message is
     * to be printed.
     *
     * */
    public PrintStream semantError(AbstractSymbol filename, TreeNode t) {
		errorStream.print(filename + ":" + t.getLineNumber() + ": ");
		return semantError();
    }

    /** Increments semantic error count and returns the print stream for
     * error messages.
     *
     * @return a print stream to which the error message is
     * to be printed.
     *
     * */
    public PrintStream semantError() {
	semantErrors++;
	return errorStream;
    }

    /** Returns true if there are any static semantic errors. */
    public boolean errors() {
	return semantErrors != 0;
    }

	/* verificar herencia */
	public boolean verificaHerencia(AbstractSymbol nombreClase)
	{
		// obtener la informacion de la clase
		class_c clase = (class_c)tablaClase.lookup(nombreClase);
		while(clase != null)
		{
			// obtener el nombre de la clase padre
			AbstractSymbol nombrePadre = clase.getParent();
			// si hereda de object entonces no se verifica nada mas
			if(nombrePadre == TreeConstants.Object_)
			{
				return false;
			}
			// si el nombre de la clase es igual al del padre entonces hay ciclo
			// en la herencia
			if(nombreClase == nombrePadre)
			{
				return true;
			}
			// si no hay ciclo con el padre inmediato se verifica con sus ancestros
			// con recursion desde las hojas
			clase = (class_c)tablaClase.lookup(nombrePadre);
		}
		return false;
	}

		// para poder ver en la tabla de clases
		public class_c lookup(AbstractSymbol nombreClase)
		{
			return (class_c)tablaClase.lookup(nombreClase);
		}

		// obtener informacion de un metodo de un ancestro
		public method lookupMetodo(AbstractSymbol nombreMetodo, AbstractSymbol nombrePadre)
		{
			class_c clase = (class_c)tablaClase.lookup(nombrePadre);
			while(clase != null)
			{
				method metodo = (method)clase.metodo(nombreMetodo);
				if (metodo != null)
				{
					return metodo;
				}
				// si no buscar en los ancestros con recursion
				clase = (class_c)tablaClase.lookup(clase.getParent());
			}
			return null;
		}

		// sabe si un tipo(clase) ya fue definido
		public boolean existeTipo(AbstractSymbol nombreClase){
			if(nombreClase == TreeConstants.SELF_TYPE)
			{
				return true;
			}
			else if(tablaClase.lookup(nombreClase) == null)
			{
				return false;
			}
			else
			{
				return true;
			}
		}

		public AbstractSymbol lookupObjeto(AbstractSymbol nombreObjeto, AbstractSymbol nombrePadre)
		{
			class_c clase = (class_c)tablaClase.lookup(nombrePadre);
			while(clase != null)
			{
				AbstractSymbol objeto = (AbstractSymbol)clase.objeto(nombreObjeto);
				if(objeto != null)
				{
					return objeto;
				}
				// si no esta en la clase buscar en los ancestros
				clase = (class_c)tablaClase.lookup(clase.getParent());
			}
			// si no esta definido el tipo se regresa no_type
			return TreeConstants.No_type;
		}

		public boolean subTipo(AbstractSymbol nombreClase, AbstractSymbol nombrePadre, AbstractSymbol nombreClasePrincipal )
		{
			// SELF_TYPE <= SELF_TYPE
			if((nombreClase == TreeConstants.SELF_TYPE) && nombrePadre == TreeConstants.SELF_TYPE)
			{
				return true;
			}
			// Error: nombreClase <= SELF_TYPE
			if(nombrePadre == TreeConstants.SELF_TYPE)
			{
				return false;
			}

			class_c clase, clasePadre;
			// si es el caso <expr>@<type>.id(<expr>,...,<expr>)
			if(nombreClase == TreeConstants.SELF_TYPE){
				clase = (class_c)tablaClase.lookup(nombreClasePrincipal);
			}
			else{
				clase = (class_c)tablaClase.lookup(nombreClase);
			}

			// caso del satatic dispatch
			clasePadre = (class_c)tablaClase.lookup(nombrePadre);
			if((clase == null) || (clasePadre == null))
			{
				SemantErrors.undefinedTypeInClass(clase.getName(), clasePadre.getName(), nombreClasePrincipal, semantError((class_c)tablaClase.lookup(nombreClasePrincipal)));
				return false;
			}

			while(clase != null)
			{
				if(clase.getName() == nombrePadre)
				{
					return true;
				}
				// ver en los ancestros
				clase = (class_c)tablaClase.lookup(clase.getParent());
			}
			return false;
		}

		public AbstractSymbol lub(AbstractSymbol nombreClase1, AbstractSymbol nombreClase2, AbstractSymbol nombreClasePrincipal)
		{
			if ((nombreClase1 == TreeConstants.SELF_TYPE) && (nombreClase2 == TreeConstants.SELF_TYPE))
			{
				// SELF_TYPE <= SELF_TYPE
				return TreeConstants.SELF_TYPE;
			}

			if(nombreClase1 == TreeConstants.SELF_TYPE)
			{
				nombreClase1 = nombreClasePrincipal;
			}
			if(nombreClase2 == TreeConstants.SELF_TYPE)
			{
				nombreClase2 = nombreClasePrincipal;
			}

			class_c clase1 = (class_c)tablaClase.lookup(nombreClase1);
			class_c clase2 = (class_c)tablaClase.lookup(nombreClase2);

			if((clase1 == null) || (clase2 == null))
			{
				SemantErrors.hasUndefinedBranch(nombreClase1, nombreClase2, nombreClasePrincipal, semantError((class_c)tablaClase.lookup(nombreClasePrincipal)));
				return TreeConstants.Object_;
			}

			while(clase1 != null)
			{
				if(subTipo(nombreClase2, clase1.getName(), nombreClasePrincipal))
				{
					return clase1.getName();
				}
				clase1 = (class_c)tablaClase.lookup(clase1.getParent());
			}

			PrintStream p = semantError((class_c)tablaClase.lookup(nombreClasePrincipal));
        	p.println("Malformed inheritance tree detected by least-upper-bound called in class " + nombreClasePrincipal);
    		p.println("May be caused by undefined type in type attribute or method formal param.");
			return TreeConstants.Object_;
		}
}
