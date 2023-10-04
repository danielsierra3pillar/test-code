import java.io.PrintStream;

public final class SemantErrors {

    // Cuando una clase ha sido declarada previamente
    public static void classPreviouslyDefined(AbstractSymbol name, PrintStream p) {
        p.println("Class " + name + " cannot be re-defined.");
        System.err.println("Compilation halted due to static semantic errors.");
    }

    // Cuando una clase basica es redefinida

    public static void basicClassRedefined(AbstractSymbol name, PrintStream p) {
        p.println("Cannot redefine basic class " + name + ".");
        System.err.println("Compilation halted due to static semantic errors.");
    }

    // Cuando el padre de una clase no existe
    public static void inheritsFromAnUndefinedClass(AbstractSymbol name, AbstractSymbol parent, PrintStream p) {
        p.println("Class " + name + " inherits from an undefined class " + parent + ".");
        System.err.println("Compilation halted due to static semantic errors.");
    }

    // Cuando una clase hereda de una clase basica
    public static void cannotInheritClass(AbstractSymbol name, AbstractSymbol parent, PrintStream p) {
        p.println("Class " + name + " cannot inherit from class " + parent + ".");
        System.err.println("Compilation halted due to static semantic errors.");
    }

    // Cuando una clase esta en un ciclo de herencia
    public static void inheritanceCycle(AbstractSymbol name, PrintStream p) {
        p.println("Class " + name + " forms inheritance loop");
        System.err.println("Compilation halted due to static semantic errors.");
    }

    // Cuando un metodo se define varias veces en una clase
    public static void methodMultiplyDefined(AbstractSymbol name, PrintStream p) {
        p.println("Method " + name + " is multiply defined.");
    }

    // Cuando un atributo se define varias veces en una clase
    public static void attrMultiplyDefined(AbstractSymbol name, PrintStream p) {
        p.println("Attribute " + name + " is multiply defined in class.");
    }

    // Cuando un atributo se redefine porque se heredo de alguna clase padre
    public static void attrOfAnInheritedClass(AbstractSymbol name, PrintStream p) {
        p.println("Attr " + name + " is defined in ancestor class.");
    }

    // Cuando la clase main no esta definida
    public static void noClassMain(PrintStream p) {
        p.println("Class Main is not defined.");
    }

    // Cuando no hay un metodo main en la clase Main
    public static void noMainMethodInMainClass(PrintStream p) {
        p.println("Method Main.main() is not defined.");
    }

    // Cuando el metodo main de la clase Main no deberia de llevar argumentos
    public static void mainMethodNoArgs(PrintStream p) {
        p.println("Method Main.main() should not have any formal parameters.");
    }

    // Cuando definen un formal con nombre self
    public static void formalCannotBeSelf(PrintStream p) {
        p.println("'self' cannot be the name of a formal parameter.");
    }

    // Cuando declaran un formal parameter con tipo SELF_TYPE
    public static void formalParamCannotHaveTypeSELF_TYPE(AbstractSymbol name, PrintStream p) {
        p.println("formal " + name + " cannot have SELF_TYPE as its declared type");
    }

    // Cuando un parametro es definido multiples veces
    public static void formalMultiplyDefined(AbstractSymbol name, PrintStream p) {
        p.println("Formal parameter " + name + " is multiply defined.");
    }

    // Cuando un metodo es heredado tiene diferente numero de formals parameters
    public static void diffNumOfFormalsRedefinedMethod(AbstractSymbol name, PrintStream p) {
        p.println("Incompatible number of formal parameters in redefined method " + name + ".");
    }

    // Cuando un metodo es heredado y sus formals tienen diferente tipo al original
    public static void diffTypeFormalRedefinedMethod(AbstractSymbol name, AbstractSymbol t1, AbstractSymbol t2,
            AbstractSymbol t3, PrintStream p) {
        p.println("Formal " + name + " of redefined method " + t1 + " has different type " + t2 + " than original type "
                + t3);
    }

    // Cuando el tipo declarado de un metodo no es igual al retornado
    public static void diffReturnType(AbstractSymbol type, AbstractSymbol name, AbstractSymbol returnt, PrintStream p) {
        p.println("Inferred return type " + type + " of method " + name + " does not conform to declared return type "
                + returnt + ".");
    }

    // cuando el return type no existe
    public static void undefinedReturnType(AbstractSymbol rt, AbstractSymbol methodName, PrintStream p) {
        p.println("Undefined return type " + rt + " in method " + methodName + ".");
    }

    // Cuando declaran un atributo con nombre self
    public static void selfCannotBeTheNameOfAttr(PrintStream p) {
        p.println("'self' used as an attribute");
    }

    // Cuando se declara un atributo con un tipo no definido
    public static void classOfAttrIsUndefined(AbstractSymbol t, AbstractSymbol name, PrintStream p) {
        p.println("Declared type " + t + " of attr " + name + " is undefined.");
    }

    // Cuando se inicializa un atributo con un tipo diferente al declarado
    public static void diffInitType(AbstractSymbol it, AbstractSymbol name, AbstractSymbol decl, PrintStream p) {
        p.println("Inferred type " + it + " of initialization of attribute " + name
                + " does not conform to declared type " + decl + ".");
    }

    // Cuando se le asigna un tipo diferente a una variable
    public static void diffAssignType(AbstractSymbol it, AbstractSymbol decl, AbstractSymbol name, PrintStream p) {
        p.println("Type " + it + " of assigned expression does not conform to declared type " + decl + " of identifier "
                + name + ".");
    }

    // Cuando se trata asignar a una variable no definida
    public static void assignToUndeclaredVariable(AbstractSymbol name, PrintStream p) {
        p.println("Assignment to undeclared variable " + name + ".");
    }

    // Cuando se trata de llamar a un staticdispatch con expr diferente tipo
    public static void exprNotConformToDeclaredSDType(AbstractSymbol et, AbstractSymbol sdt, PrintStream p) {
        p.println("Expression type " + et + " does not conform to declared static dispatch type " + sdt + ".");
    }

    // Cuando se hace un new con una clase no definida
    public static void newUndefinedClass(AbstractSymbol type_name, PrintStream p) {
        p.println("new type " + type_name + " is undefined.");
    }

    // Cuando se llama a un static dispatch no definido
    public static void staticDispatchUndefined(AbstractSymbol name, PrintStream p) {
        p.println("Static dispatch to undefined method " + name + ".");
    }

    // Cuando se manda a llamar un metodo con diferente numero de argumentos (Static
    // dispatch)
    public static void methodInvokedWithWrongNumberOfArgs(AbstractSymbol name, AbstractSymbol t1, int t2, int t3,
            PrintStream p) {
        p.println("Method " + name + " in class " + t1 + " expects " + t2 + " arguments, but got " + t3);
    }

    // Cuando el argumento enviado a un metodo es diferente tipo al declarado en el
    // formal
    public static void parameterDiffType(AbstractSymbol name, AbstractSymbol t1, AbstractSymbol t2, PrintStream p) {
        p.println("Inferred type " + t1 + " of param " + name + " does not conform to expected type " + t2);
    }

    // Cuando se manda a llamar a un metodo no definido
    public static void dispatchToUndefinedMethod(AbstractSymbol name, PrintStream p) {
        p.println("Dispatch to undefined method " + name + ".");
    }

    // Cuando se manda a llamar a un metodo con diferente numero de argumentos
    // (dispatch)
    public static void methodCalledWithWrongNbofArgs(AbstractSymbol name, PrintStream p) {
        p.println("Method " + name + " called with wrong number of arguments.");
    }

    // Cuando el if no tiene un predicate bool
    public static void ifNoBoolPredicate(AbstractSymbol t1, AbstractSymbol t2, AbstractSymbol t3, PrintStream p) {
        p.println("Non-Bool argument: if" + t1 + " then " + t2 + " else " + t3);
    }

    // Cuando el while no tiene condition de tipo bool
    public static void whileNoBoolCondition(AbstractSymbol t1, AbstractSymbol t2, PrintStream p) {
        p.println("Non-Bool argument: while " + t1 + " loop " + t2 + " pool");
    }

    // Cuando el tipo de un branch del case se duplica
    public static void duplicateBranch(AbstractSymbol t, PrintStream p) {
        p.println("Duplicate branch " + t + " in case statement.");
    }

    // Cuando una variable en el let se declara con un tipo no definido
    public static void letUndefinedType(AbstractSymbol t, AbstractSymbol id, PrintStream p) {
        p.println("Declared type " + t + " of let variable " + id + " is undefined.");
    }

    // Cuando el nombre de alguna variable declarada en let es llamada self
    public static void selfCannotBeBoundInALet(PrintStream p) {
        p.println("binding 'self' in a 'let'");
    }

    public static void selfCannotAssign(PrintStream p) {
        p.println("'self' cannot be bound in a 'let' expression");
    }

    // Cuando en un let se inicializa una variable con un tipo diferente al
    // declarado
    public static void letDiffInitType(AbstractSymbol t, AbstractSymbol id, AbstractSymbol decl, PrintStream p) {
        p.println("Inferred type " + t + " does not conform to declared type " + decl + " in let init for var " + id);
    }

    // Cuando se manda a llamar una operacion con diferente tipo de int
    public static void noIntArguments(AbstractSymbol t1, AbstractSymbol t2, String op, PrintStream p) {
        p.println("Non-Int arguments: " + t1 + " " + op + " " + t2);
    }

    // Cuando el neg tiene un tipo diferente de Int
    public static void argumentOfNegNoIntType(AbstractSymbol t, PrintStream p) {
        p.println("'~' expression got arg of type " + t + ", expected Int");
    }

    // Cuando se compara ilegalmente con tipos basicos
    public static void illegalCompWithABasicType(PrintStream p) {
        p.println("Illegal comparison with a basic type.");
    }

    // Cuando se asigna self
    public static void cannotAssignSelf(PrintStream p) {
        p.println("assign to 'self'");
    }

    /**
     * Si un identifier(variable) no se encuentra en la symbol Table
     */
    public static void undeclaredIdentifiers(AbstractSymbol id_name, PrintStream p) {
        p.println("Undeclared identifier: " + id_name);
    }

    /**
     * Si se realiza un new pero la clase que se intenta crear no se definio
     */
    public static void newUsedWithUndefinedClass(AbstractSymbol class_name, PrintStream p) {
        p.println("'new' used with undefined class " + class_name + ".");
    }

    /**
     * Si el not tiene como parametro algo que no sea Bool
     */
    public static void notNotBoolType(AbstractSymbol invalid_type, PrintStream p) {
        p.println("'not' expression got arg of type " + invalid_type + ", expected Bool");
    }

    // caso de dispatch
    public static void undefinedTypeInClass(AbstractSymbol t1, AbstractSymbol t2, AbstractSymbol t3, PrintStream p) {
        p.println("Undefined type in class " + t3 + ".  May be " + t1 + " or " + t2);
    }

    //
    public static void hasUndefinedBranch(AbstractSymbol t1, AbstractSymbol t2, AbstractSymbol t3, PrintStream p) {
        p.println("Case statement in class " + t3 + "has undefined branch-types.  Maybe " + t1 + " or " + t2);
    }

    //
    public static void methodRedefined(AbstractSymbol t1, AbstractSymbol t2, PrintStream p) {
        p.println("Method " + t1 + " redefined in class " + t2);
    }

    //
    public static void attrRedefined(AbstractSymbol t1, AbstractSymbol t2, PrintStream p) {
        p.println("Attribute " + t1 + " redefined in class " + t2);
    }

    //
    public static void methodRedefinedAncestor(AbstractSymbol t1, AbstractSymbol t2, AbstractSymbol t3, PrintStream p) {
        p.println("Redefined method " + t1 + " has different return type " + t2 + " than original return type " + t3);
    }

    //
    public static void methodArgsRedefinedAncestor(AbstractSymbol t1, PrintStream p) {
        p.println("Redefined method " + t1 + " has different number of args as previous declaration");
    }

    // Cuando declaran un formal con nombre self
    public static void selfCannotBeTheNameOfFormal(PrintStream p) {
        p.println("'self' used as formal parameter");
    }

    // tipo de formal no definido
    public static void undefinedTypeOfFormal(AbstractSymbol t1, AbstractSymbol t2, PrintStream p) {
        p.println("formal " + t1 + ":" + t2 + " has undefined type.");
    }

    // cuando el return type de un metodo no esta definido
    public static void undefinedReturnTypeMethod(AbstractSymbol rt, PrintStream p) {
        p.println("Method return type " + rt + " is undefined.");
    }

    public static void diffInitTypeMethod(AbstractSymbol t1, AbstractSymbol t2, PrintStream p) {
        p.println("Inferred type " + t1 + " of method body does not conform to declared return type " + t2);
    }

    // Cuando se inicializa un atributo con un tipo diferente al declarado
    public static void diffInitTypeAttr(AbstractSymbol it, AbstractSymbol decl, PrintStream p) {
        p.println("Inferred type " + it + " does not conform to declared type " + decl + " in attr init");
    }

    // tipo de branch no definido
    public static void undefinedTypeBranch(AbstractSymbol t1, AbstractSymbol t2, PrintStream p) {
        p.println("Declared type " + t1 + " of branch variable " + t2 + " is undefined.");
    }

    // tipo de branch definido como SELF_TYPE
    public static void selfTypeBranch(PrintStream p) {
        p.println("'SELF_TYPE' as declared type in case branch");
    }

    // tipo de branch definido como SELF_TYPE
    public static void selfNameCase(PrintStream p) {
        p.println("binding 'self' in case");
    }

    public static void invalidTypeForAssign(AbstractSymbol t1, AbstractSymbol t2, PrintStream p) {
        p.println("Invalid type: " + t1 + " for assign identifier " + t2);
    }

    public static void noSubtypeAssign(AbstractSymbol t1, AbstractSymbol t2, PrintStream p) {
        p.println("Non-conforming arguments: " + t1 + " <- " + t2);
    }

    public static void dispatchFromSelfType(AbstractSymbol t1, PrintStream p) {
        p.println("Cannot statically dispatch method " + t1 + " from SELF_TYPE");
    }

    public static void dispatchFromUndefined(AbstractSymbol t1, AbstractSymbol t2, PrintStream p) {
        p.println("Cannot statically dispatch method " + t1 + " from undefined class " + t2);
    }

    public static void dispatchNoSubtype(AbstractSymbol t1, AbstractSymbol t2, PrintStream p) {
        p.println(t1 + "@" + t2 + " does not conform " + "in static dispatch");
    }

    public static void methodUndefined(AbstractSymbol t1, AbstractSymbol t2, PrintStream p) {
        p.println("Method " + t1 + " not defined in class " + t2 + " or any of its ancestors");
    }

    // Cuando se compara ilegalmente con tipos basicos
    public static void illegalCompWithABasicTypeInt(AbstractSymbol t1, AbstractSymbol t2, PrintStream p) {
        p.println("'=' expression got args of type " + t1 + " and " + t2
                + ".  Can only compare 2 expressions of type Int");
    }

    // Cuando se compara ilegalmente con tipos basicos
    public static void illegalCompWithABasicTypeBool(AbstractSymbol t1, AbstractSymbol t2, PrintStream p) {
        p.println("'==' expression got args of type " + t1 + " and " + t2
                + ".  Can only compare 2 expressions of type Bool");
    }

    // Cuando se compara ilegalmente con tipos basicos
    public static void illegalCompWithABasicTypeStr(AbstractSymbol t1, AbstractSymbol t2, PrintStream p) {
        p.println("'==' expression got args of type " + t1 + " and " + t2
                + ".  Can only compare 2 expressions of type String");
    }
}
