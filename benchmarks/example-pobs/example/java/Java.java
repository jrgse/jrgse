/*
 * Copyright (c) 2004 Parser OBjectS group
 * Released under ZLib/LibPNG license. See "license.txt" for details.
 */
package example.java;

import pobs.*;
import pobs.parser.*;
import pobs.utility.POBS;

/**
 * Insert the type's description here.
 * 
 * @author: Martijn W. van der Lee
 */
public class Java extends pobs.PParser {
    public pobs.PObject parser;
    /**
     * Java constructor comment.
     * Initializes the Java parser following the Java Syntax Specification
     */
    public Java() {
        super();
        //???: POBS equiv for "bnf ::= blah? yodel? hiti?" construct; POptionals

        // Speedy gonzales bits:
        PObject c_zero = new PChar('0');
        PObject c_parenL = new PChar('(');
        PObject c_parenR = new PChar(')');
        PObject c_blockL = new PChar('[');
        PObject c_blockR = new PChar(']');
        PObject c_accolL = new PChar('{');
        PObject c_accolR = new PChar('}');
        PObject c_colon = new PChar(':');
        PObject c_semicolon = new PChar(';');
        PObject c_comma = new PChar(',');
        PObject c_dot = new PChar('.');
        PObject c_quote = new PChar('"');
        PObject c_apos = new PChar('\'');
        PObject c_star = new PChar('*');

        // Forward iterator pointers
        PPointer ptr_array_initializer = new PPointer();
        PPointer ptr_type = new PPointer();
        PPointer ptr_block = new PPointer();
        PPointer ptr_block_statements = new PPointer();
        PPointer ptr_statement = new PPointer();
        PPointer ptr_statement_no_short_if = new PPointer();
        PPointer ptr_expression = new PPointer();
        PPointer ptr_assignment_expression = new PPointer();
        PPointer ptr_conditional_expression = new PPointer();
        PPointer ptr_conditional_or_expression = new PPointer();
        PPointer ptr_conditional_and_expression = new PPointer();
        PPointer ptr_inclusive_or_expression = new PPointer();
        PPointer ptr_exclusive_or_expression = new PPointer();
        PPointer ptr_and_expression = new PPointer();
        PPointer ptr_equality_expression = new PPointer();
        PPointer ptr_relational_expression = new PPointer();
        PPointer ptr_shift_expression = new PPointer();
        PPointer ptr_additive_expression = new PPointer();
        PPointer ptr_multiplicative_expression = new PPointer();
        PPointer ptr_unary_expression = new PPointer();
        PPointer ptr_cast_expression = new PPointer();
        PPointer ptr_postfix_expression = new PPointer();
        PPointer ptr_field_access = new PPointer();
        PPointer ptr_method_invocation = new PPointer();
        PPointer ptr_primary_no_new_array = new PPointer();

        // Lexical parser portion of the code:
        //TODO: Make REAL identifier class!
        //TODO: Whitespace tokens!
        PObject identifier = POBS.ALPHA;
        PObject input_character = new PAny();
        PObject escape_character =
            new PSequence(new PToken("\\u"), new PRepeat(POBS.HEX_DIGIT, 4, 4));
        PObject escape_sequence = escape_character;

        // Tokens
        PObject non_zero_digit = new PSet("123456789");

        PObject digit = POBS.DIGIT;

        PObject digits = new PMultiple(digit);

        PObject null_literal = new PToken("null");

        PObject string_character =
            new POr(
                new PExcept(input_character, new PSet("\"\\")),
                escape_character);

        PObject string_characters = new PMultiple(string_character);

        PObject string_literal =
            new PSequence(c_quote, new POptional(string_characters), c_quote);

        PObject single_character =
            new PExcept(input_character, new PSet("\'\\"));

        PObject character_literal =
            new PSequence(
                c_apos,
                new POr(single_character, escape_sequence),
                c_apos);

        PObject boolean_literal =
            new POr(new PToken("true"), new PToken("false"));

        PObject float_type_suffix = new PSet("fFdD");

        PObject sign = POBS.SIGN;

        PObject signed_integer = new PSequence(new POptional(sign), digits);

        PObject exponent_indicator = new PSet("eE");

        PObject exponent_part =
            new PSequence(exponent_indicator, signed_integer);

        PObject floating_point_literal =
            new PSequence(
                new PObject[] {
                    digits,
                    c_dot,
                    new POptional(digits),
                    new POptional(exponent_part),
                    new POptional(float_type_suffix),
                    digits,
                    new POptional(exponent_part),
                    new POptional(float_type_suffix)});
        PObject octal_digit = POBS.OCTAL_DIGIT;

        PObject octal_numeral =
            new PSequence(c_zero, new PMultiple(octal_digit));

        PObject hex_digit = POBS.HEX_DIGIT;

        PObject hex_numeral =
            new PSequence(c_zero, new PSet("xX"), new PMultiple(hex_digit));

        PObject decimal_numeral =
            new POr(
                c_zero,
                new PSequence(non_zero_digit, new POptional(digits)));

        PObject integer_type_suffix = new PSet("lL");

        PObject octal_integer_literal =
            new PSequence(octal_numeral, new POptional(integer_type_suffix));

        PObject hex_integer_literal =
            new PSequence(hex_numeral, new POptional(integer_type_suffix));

        PObject decimal_integer_literal =
            new PSequence(decimal_numeral, new POptional(integer_type_suffix));

        PObject integer_literal =
            new POr(
                decimal_integer_literal,
                hex_integer_literal,
                octal_integer_literal);

        PObject literal =
            new POr(
                new PObject[] {
                    integer_literal,
                    floating_point_literal,
                    boolean_literal,
                    character_literal,
                    string_literal,
                    null_literal });

        PObject simple_type_name = identifier;

        PObject package_name = new PList(identifier, c_dot, 1, PList.INFINITE);

        PObject type_name = package_name;

        PObject expression_name = package_name;

        PObject method_name = package_name;

        PObject array_type = new PSequence(ptr_type, c_blockL, c_blockR);

        PObject interface_type = type_name;

        PObject class_type = type_name;

        PObject class_type_list = new PList(class_type, c_comma);

        PObject throws_declaration =
            new PSequence(new PToken("throws"), class_type_list);

        PObject variable_declarator_id =
            new PSequence(
                identifier,
                new PKleene(new PSequence(c_blockL, c_blockR)));

        PObject formal_parameter =
            new PSequence(ptr_type, variable_declarator_id);

        PObject formal_parameter_list = new PList(formal_parameter, c_comma);

        PObject variable_initializer =
            new POr(ptr_expression, ptr_array_initializer);

        PObject variable_initializers = new PMultiple(variable_initializer);

        PObject array_initializer =
            new PSequence(
                c_accolL,
                new POptional(variable_initializers),
                new POptional(c_comma),
                c_accolR);
        ptr_array_initializer.set(array_initializer);

        PObject variable_declarator =
            new PSequence(
                variable_declarator_id,
                new POptional(
                    new PSequence(new PChar('='), variable_initializer)));

        PObject variable_declarators = new PList(variable_declarator, c_comma);

        PObject class_or_interface_type = new POr(class_type, interface_type);

        PObject reference_type = new POr(class_or_interface_type, array_type);

        PObject floating_point_type = new PTokens("float", "double");

        PObject integral_type =
            new PTokens("byte", "short", "int", "long", "char");

        PObject numeric_type = new POr(integral_type, floating_point_type);

        PObject primitive_type = new POr(numeric_type, new PToken("boolean"));

        PObject type = new POr(primitive_type, reference_type);
        ptr_type.set(type);

        PObject array_access =
            new PSequence(
                new POr(expression_name, ptr_primary_no_new_array),
                c_blockL,
                ptr_expression,
                c_blockR);

        PObject dims = new PMultiple(new PSequence(c_blockL, c_blockR));

        PObject dim_expr = new PSequence(c_blockL, ptr_expression, c_blockR);

        PObject dim_exprs = new PMultiple(dim_expr);

        PObject array_creation_expression =
            new PSequence(
                new PToken("new"),
                new POr(primitive_type, class_or_interface_type),
                dim_exprs,
                new POptional(dims));
        PObject argument_list = new PList(ptr_expression, c_comma);

        PObject class_instance_creation_expression =
            new PSequence(
                new PToken("new"),
                class_type,
                c_parenL,
                new POptional(argument_list),
                c_parenR);

        PObject primary_no_new_array =
            new POr(
                new PObject[] {
                    literal,
                    new PToken("this"),
                    new PSequence(c_parenL, ptr_expression, c_parenR),
                    class_instance_creation_expression,
                    ptr_field_access,
                    ptr_method_invocation,
                    array_access });
        ptr_primary_no_new_array.set(primary_no_new_array);

        PObject primary =
            new POr(primary_no_new_array, array_creation_expression);

        PObject field_access =
            new PSequence(
                new POr(primary, new PToken("super")),
                c_dot,
                identifier);
        ptr_field_access.set(field_access);

        PObject method_invocation =
            new PSequence(
                new POr(
                    method_name,
                    new PSequence(
                        new POr(primary, new PToken("super")),
                        c_dot,
                        identifier)),
                c_parenL,
                new POptional(argument_list),
                c_parenR);
        ptr_method_invocation.set(method_invocation);

        PObject postincrement_expression =
            new PSequence(ptr_postfix_expression, new PToken("++"));

        PObject postdecrement_expression =
            new PSequence(ptr_postfix_expression, new PToken("--"));

        PObject postfix_expression =
            new POr(
                primary,
                expression_name,
                postincrement_expression,
                postdecrement_expression);
        ptr_postfix_expression.set(postfix_expression);

        PObject unary_expression_not_plus_minus =
            new POr(
                postfix_expression,
                new PSequence(new PChar('~'), ptr_unary_expression),
                new PSequence(new PChar('!'), ptr_unary_expression),
                ptr_cast_expression);

        PObject preincrement_expression =
            new PSequence(new PToken("++"), ptr_unary_expression);

        PObject predecrement_expression =
            new PSequence(new PToken("--"), ptr_unary_expression);

        PObject unary_expression =
            new POr(
                preincrement_expression,
                predecrement_expression,
                new PSequence(new PChar('+'), ptr_unary_expression),
                new PSequence(new PChar('-'), ptr_unary_expression),
                unary_expression_not_plus_minus);
        ptr_unary_expression.set(unary_expression);

        PObject cast_expression =
            new POr(
                new PSequence(
                    c_parenL,
                    primitive_type,
                    c_parenR,
                    unary_expression),
                new PSequence(
                    c_parenL,
                    reference_type,
                    c_parenR,
                    unary_expression_not_plus_minus));
        ptr_cast_expression.set(cast_expression);

        PObject multiplicative_expression =
            new POr(
                unary_expression,
                new PSequence(
                    ptr_multiplicative_expression,
                    new PChar('*'),
                    unary_expression),
                new PSequence(
                    ptr_multiplicative_expression,
                    new PChar('/'),
                    unary_expression),
                new PSequence(
                    ptr_multiplicative_expression,
                    new PChar('%'),
                    unary_expression));
        ptr_multiplicative_expression.set(multiplicative_expression);

        PObject additive_expression =
            new POr(
                multiplicative_expression,
                new PSequence(
                    ptr_additive_expression,
                    new PChar('+'),
                    multiplicative_expression),
                new PSequence(
                    ptr_additive_expression,
                    new PChar('-'),
                    multiplicative_expression));
        ptr_additive_expression.set(additive_expression);

        PObject shift_expression =
            new POr(
                additive_expression,
                new PSequence(
                    ptr_shift_expression,
                    new PToken("<<"),
                    additive_expression),
                new PSequence(
                    ptr_shift_expression,
                    new PToken(">>"),
                    additive_expression),
                new PSequence(
                    ptr_shift_expression,
                    new PToken(">>>"),
                    additive_expression));
        ptr_shift_expression.set(shift_expression);

        PObject relational_expression =
            new POr(
                new PObject[] {
                    shift_expression,
                    new PSequence(
                        ptr_relational_expression,
                        new PChar('<'),
                        shift_expression),
                    new PSequence(
                        ptr_relational_expression,
                        new PToken(">"),
                        shift_expression),
                    new PSequence(
                        ptr_relational_expression,
                        new PToken("<="),
                        shift_expression),
                    new PSequence(
                        ptr_relational_expression,
                        new PToken(">="),
                        shift_expression),
                    new PSequence(
                        ptr_relational_expression,
                        new PToken("instanceof"),
                        reference_type)});
        ptr_relational_expression.set(relational_expression);

        PObject equality_expression =
            new POr(
                relational_expression,
                new PSequence(
                    ptr_equality_expression,
                    new PToken("=="),
                    relational_expression),
                new PSequence(
                    ptr_equality_expression,
                    new PToken("!="),
                    relational_expression));
        ptr_equality_expression.set(equality_expression);

        PObject and_expression =
            new POr(
                equality_expression,
                new PSequence(
                    ptr_and_expression,
                    new PChar('&'),
                    equality_expression));
        ptr_and_expression.set(and_expression);

        PObject exclusive_or_expression =
            new POr(
                and_expression,
                new PSequence(
                    ptr_exclusive_or_expression,
                    new PChar('^'),
                    and_expression));
        ptr_exclusive_or_expression.set(exclusive_or_expression);

        PObject inclusive_or_expression =
            new POr(
                exclusive_or_expression,
                new PSequence(
                    ptr_inclusive_or_expression,
                    new PChar('|'),
                    exclusive_or_expression));
        ptr_inclusive_or_expression.set(inclusive_or_expression);

        PObject conditional_and_expression =
            new POr(
                inclusive_or_expression,
                new PSequence(
                    ptr_conditional_and_expression,
                    new PToken("||"),
                    inclusive_or_expression));
        ptr_conditional_and_expression.set(conditional_and_expression);

        PObject conditional_or_expression =
            new POr(
                conditional_and_expression,
                new PSequence(
                    ptr_conditional_or_expression,
                    new PToken("&&"),
                    conditional_and_expression));
        ptr_conditional_or_expression.set(conditional_or_expression);

        PObject conditional_expression =
            new POr(
                conditional_or_expression,
                new PSequence(
                    conditional_or_expression,
                    new PChar('?'),
                    ptr_expression,
                    c_colon,
                    ptr_conditional_expression));
        ptr_conditional_expression.set(conditional_expression);

        PObject assignment_operator =
            new PTokens(
                new String[] {
                    "=",
                    "*=",
                    "/=",
                    "%=",
                    "+=",
                    "-=",
                    "<<=",
                    ">>=",
                    ">>>=",
                    "&=",
                    "^=",
                    "|=" });

        PObject left_hand_side =
            new POr(expression_name, field_access, array_access);

        PObject assignment =
            new PSequence(
                left_hand_side,
                assignment_operator,
                ptr_assignment_expression);

        PObject assignment_expression =
            new POr(conditional_expression, assignment);
        ptr_assignment_expression.set(assignment_expression);

        PObject expression = assignment_expression;
        ptr_expression.set(expression);

        PObject constant_expression = expression;

        PObject finally_clause =
            new PSequence(new PToken("finally"), ptr_block);

        PObject catch_clause =
            new PSequence(
                new PToken("catch"),
                c_parenL,
                formal_parameter,
                c_parenR,
                ptr_block);

        PObject catches = new PMultiple(catch_clause);

        PObject try_statement =
            new PSequence(
                new PToken("try"),
                ptr_block,
                new POr(
                    catches,
                    new PSequence(new POptional(catches), finally_clause)));

        PObject synchronized_statement =
            new PSequence(
                new PToken("synchronized"),
                c_parenL,
                expression,
                c_parenR,
                ptr_block);

        PObject throws_statement =
            new PSequence(new PToken("throw"), expression, c_semicolon);

        PObject return_statement =
            new PSequence(
                new PToken("return"),
                new POptional(expression),
                c_semicolon);

        PObject continue_statement =
            new PSequence(
                new PToken("continue"),
                new POptional(identifier),
                c_semicolon);

        PObject break_statement =
            new PSequence(
                new PToken("break"),
                new POptional(identifier),
                c_semicolon);

        PObject statement_expression =
            new POr(
                new PObject[] {
                    assignment,
                    preincrement_expression,
                    postincrement_expression,
                    predecrement_expression,
                    postdecrement_expression,
                    method_invocation,
                    class_instance_creation_expression });

        PObject statement_expression_list = new PMultiple(statement_expression);

        PObject for_update = statement_expression_list;

        PObject local_variable_declaration =
            new PSequence(type, variable_declarators);

        PObject for_init =
            new POr(statement_expression_list, local_variable_declaration);

        PObject for_statement_no_short_if =
            new PSequence(
                new PObject[] {
                    new PToken("for"),
                    c_parenL,
                    new POptional(for_init),
                    c_semicolon,
                    new POptional(expression),
                    c_semicolon,
                    new POptional(for_update),
                    c_parenR,
                    ptr_statement_no_short_if });

        PObject for_statement =
            new PSequence(
                new PObject[] {
                    new PToken("for"),
                    c_parenL,
                    new POptional(for_init),
                    c_semicolon,
                    new POptional(expression),
                    c_semicolon,
                    new POptional(for_update),
                    c_parenR,
                    ptr_statement });

        PObject do_statement =
            new PSequence(
                new PObject[] {
                    new PToken("do"),
                    ptr_statement,
                    new PToken("while"),
                    c_parenL,
                    expression,
                    c_parenR,
                    c_semicolon });

        PObject while_statement_no_short_if =
            new PSequence(
                new PToken("while"),
                c_parenL,
                expression,
                c_parenR,
                ptr_statement_no_short_if);

        PObject while_statement =
            new PSequence(
                new PToken("while"),
                c_parenL,
                expression,
                c_parenR,
                ptr_statement);

        PObject switch_label =
            new POr(
                new PSequence(new PToken("case"), constant_expression, c_colon),
                new PSequence(new PToken("default"), c_colon));

        PObject switch_labels = new PMultiple(switch_label);

        PObject switch_block_statement_group =
            new POr(switch_labels, ptr_block_statements);

        PObject switch_block_statement_groups =
            new PMultiple(switch_block_statement_group);

        PObject switch_block =
            new PSequence(
                c_accolL,
                new POptional(switch_block_statement_groups),
                new POptional(switch_labels),
                c_accolR);

        PObject switch_statement =
            new PSequence(
                new PToken("switch"),
                c_parenL,
                expression,
                c_parenR,
                switch_block);

        PObject if_then_else_statement_no_short_if =
            new PSequence(
                new PObject[] {
                    new PToken("if"),
                    c_parenL,
                    expression,
                    c_parenR,
                    ptr_statement_no_short_if,
                    new PToken("else"),
                    ptr_statement_no_short_if });

        PObject if_then_else_statement =
            new PSequence(
                new PObject[] {
                    new PToken("if"),
                    c_parenL,
                    expression,
                    c_parenR,
                    ptr_statement_no_short_if,
                    new PToken("else"),
                    ptr_statement });

        PObject if_then_statement =
            new PSequence(
                new PToken("if"),
                c_parenL,
                expression,
                c_parenR,
                ptr_statement);

        PObject expression_statement =
            new PSequence(statement_expression, c_semicolon);

        PObject labeled_statement_no_short_if =
            new PSequence(identifier, c_colon, ptr_statement_no_short_if);

        PObject labeled_statement =
            new PSequence(identifier, c_colon, ptr_statement);

        PObject empty_statement = c_semicolon;

        PObject statement_without_trailing_substatement =
            new POr(
                new PObject[] {
                    ptr_block,
                    empty_statement,
                    expression_statement,
                    switch_statement,
                    do_statement,
                    break_statement,
                    continue_statement,
                    return_statement,
                    synchronized_statement,
                    throws_statement,
                    try_statement });

        PObject statement_no_short_if =
            new POr(
                new PObject[] {
                    statement_without_trailing_substatement,
                    labeled_statement_no_short_if,
                    if_then_else_statement_no_short_if,
                    while_statement_no_short_if,
                    for_statement_no_short_if });
        ptr_statement_no_short_if.set(statement_no_short_if);

        PObject statement =
            new POr(
                new PObject[] {
                    statement_without_trailing_substatement,
                    labeled_statement,
                    if_then_statement,
                    if_then_else_statement,
                    while_statement,
                    for_statement });
        ptr_statement.set(statement);

        PObject local_variable_declaration_statement =
            new PSequence(local_variable_declaration, c_semicolon);

        PObject block_statement =
            new POr(local_variable_declaration_statement, statement);

        PObject block_statements = new PMultiple(block_statement);
        ptr_block_statements.set(block_statements);

        PObject block =
            new PSequence(c_accolL, new POptional(block_statements), c_accolR);
        ptr_block.set(block);

        PObject result_type = new POr(type, new PToken("void"));

        PObject method_declarator =
            new PSequence(
                identifier,
                c_parenL,
                new POptional(formal_parameter_list),
                c_parenR);

        PObject abstract_method_modifier = new PTokens("public", "abstract");

        PObject abstract_method_modifiers =
            new PMultiple(abstract_method_modifier);

        PObject abstract_method_declaration =
            new PSequence(
                new POptional(abstract_method_modifiers),
                result_type,
                method_declarator,
                new POptional(throws_declaration),
                c_semicolon);

        PObject constant_modifiers = new PTokens("public", "static", "final");

        PObject constant_declaration =
            new PSequence(constant_modifiers, type, variable_declarator);

        PObject interface_member_declaration =
            new POr(constant_declaration, abstract_method_declaration);

        PObject interface_member_declarations =
            new PMultiple(interface_member_declaration);

        PObject interface_body =
            new PSequence(
                c_accolL,
                new POptional(interface_member_declarations),
                c_accolR);

        PObject extends_interfaces =
            new PSequence(
                new PToken("extends"),
                new PList(interface_type, c_comma, 0, PList.INFINITE));

        PObject interface_modifier = new PTokens("public", "abstract");

        PObject interface_modifiers = new PMultiple(interface_modifier);

        PObject interface_declaration =
            new PSequence(
                new POptional(interface_modifiers),
                new PToken("interface"),
                identifier,
                new POptional(extends_interfaces),
                interface_body);

        PObject method_body = new POr(block, c_semicolon);

        PObject method_modifier =
            new PTokens(
                new String[] {
                    "public",
                    "protected",
                    "private",
                    "static",
                    "abstract",
                    "final",
                    "synchronized",
                    "native" });

        PObject method_modifiers = new PMultiple(method_modifier);

        PObject method_header =
            new PSequence(
                new POptional(method_modifiers),
                result_type,
                method_declarator,
                new POptional(throws_declaration));

        PObject method_declaration = new PSequence(method_header, method_body);

        PObject field_modifier =
            new PTokens(
                new String[] {
                    "public",
                    "protected",
                    "private",
                    "static",
                    "final",
                    "transient",
                    "volatile" });

        PObject field_modifiers = new PMultiple(field_modifier);

        PObject field_declaration =
            new PSequence(
                new POptional(field_modifiers),
                type,
                variable_declarators,
                c_semicolon);

        PObject explicit_constructor_invocation =
            new PSequence(
                new PTokens("this", "super"),
                c_parenL,
                new POptional(argument_list),
                c_parenR);

        PObject constructor_body =
            new PSequence(
                c_accolL,
                new POptional(explicit_constructor_invocation),
                new POptional(block_statements),
                c_accolR);

        PObject constructor_declarator =
            new PSequence(
                simple_type_name,
                c_parenL,
                new POptional(formal_parameter_list),
                c_parenR);

        PObject constructor_modifier =
            new PTokens("public", "protected", "private");

        PObject constructor_modifiers = new PMultiple(constructor_modifier);

        PObject constructor_declaration =
            new PSequence(
                new POptional(constructor_modifiers),
                constructor_declarator,
                new POptional(throws_declaration),
                constructor_body);

        PObject static_initializer = new PSequence(new PToken("static"), block);

        PObject class_member_declaration =
            new POr(field_declaration, method_declaration);

        PObject class_body_declaration =
            new POr(
                class_member_declaration,
                static_initializer,
                constructor_declaration);

        PObject class_body_declarations = new PMultiple(class_body_declaration);

        PObject class_body =
            new PSequence(
                c_accolL,
                new POptional(class_body_declarations),
                c_accolR);

        PObject interface_type_list = new PList(interface_type, c_comma);

        PObject interfaces =
            new PSequence(new PToken("implements"), interface_type_list);

        PObject super_class = new PSequence(new PToken("extends"), class_type);

        PObject class_modifier = new PTokens("public", "abstract", "final");

        PObject class_modifiers = new PMultiple(class_modifier);

        PObject class_declaration =
            new PSequence(
                new PObject[] {
                    new POptional(class_modifiers),
                    new PToken("class"),
                    identifier,
                    new POptional(super_class),
                    new POptional(interfaces),
                    class_body });

        PObject type_declarations =
            new POr(class_declaration, interface_declaration, c_semicolon);

        PObject type_import_on_demand_declaration =
            new PSequence(
                new PToken("import"),
                package_name,
                c_dot,
                c_star,
                c_semicolon);

        PObject single_type_import_declaration =
            new PSequence(new PToken("import"), type_name, c_semicolon);

        PObject import_declaration =
            new POr(
                single_type_import_declaration,
                type_import_on_demand_declaration);

        PObject import_declarations = new PMultiple(import_declaration);

        PObject package_declaration =
            new PSequence(new PToken("package"), package_name, c_semicolon);

        PObject compilation_unit =
            new PSequence(
                new POptional(package_declaration),
                new POptional(import_declarations),
                new POptional(type_declarations));

        parser = compilation_unit;
    }

    /**
     * Starts the application.
     * @param args an array of command-line arguments
     */
    public static void main(java.lang.String[] args) {
        // Insert code to start the application here.
    }

    /**
     * @see pobs.PObject
     */
    public pobs.PMatch parse(
        pobs.PScanner input,
        long begin,
        pobs.PContext context) {
        return parser.process(input, begin, context);
    }
}
