grammar UDL;

options {
  language = Java;
}

@header {
  package test.antlr;
}

uid     
        :       baseUid
        |       listUid
        |       tableUid        
        ;
        
baseUid 
        :       ID
        ;
        
listUid
        :       '{' INDEX '}'
        |       '{' INDEX '}' 'as' ID
        ;
                
tableUid 
        :       tableHeaderUid
        |       tableFooterUid 
        |       tableBodyUid
        ;
        
tableHeaderUid
        :       '{' 'header' ':' INDEX '}'
        |       '{' 'header' ':' INDEX '}' 'as' ID
        ;
        
tableFooterUid
        :       '{' 'feader' ':' INDEX '}'
        |       '{' 'feader' ':' INDEX '}' 'as' ID
        ;
        
tableBodyUid
        :       '{' 'row' ':' INDEX ',' 'column' ':' INDEX '}'
        |       '{' 'row' ':' INDEX ',' 'column' ':' INDEX '}' 'as' ID
        |       '{' 'row' '->' ID ',' 'column' ':' INDEX '}'
        |       '{' 'row' '->' ID ',' 'column' ':' INDEX '}' 'as' ID
        |       '{' 'row' ':' INDEX ',' 'column' '->' ID '}'
        |       '{' 'row' ':' INDEX ',' 'column' '->' ID '}' 'as' ID
        |       '{' 'row' '->' ID ',' 'column' '->' ID '}'
        |       '{' 'row' '->' ID ',' 'column' '->' ID '}' 'as' ID
        |       '{' 'tbody' ':' INDEX ',' 'row' ':' INDEX ',' 'column' ':' INDEX '}'
        |       '{' 'tbody' ':' INDEX ',' 'row' ':' INDEX ',' 'column' ':' INDEX '}' 'as' ID
        |       '{' 'tbody' ':' INDEX ',' 'row' '->' ID ',' 'column' ':' INDEX '}'
        |       '{' 'tbody' ':' INDEX ',' 'row' '->' ID ',' 'column' ':' INDEX '}' 'as' ID
        |       '{' 'tbody' ':' INDEX ',' 'row' ':' INDEX ',' 'column' '->' ID '}'
        |       '{' 'tbody' ':' INDEX ',' 'row' ':' INDEX ',' 'column' '->' ID '}' 'as' ID
        |       '{' 'tbody' ':' INDEX ',' 'row' '->' ID ',' 'column' '->' ID '}'
        |       '{' 'tbody' ':' INDEX ',' 'row' '->' ID ',' 'column' '->' ID '}' 'as' ID
        ;                               

fragment LETTER : ('a'..'z' | 'A'..'Z') ;
fragment DIGIT : '0'..'9';
INDEX   :       (DIGIT+ |'all' | 'odd' | 'even' | 'any' | 'first' | 'last' );   
ID      :       LETTER (LETTER | DIGIT | '_')*;
WS      :       (' ' | '\t' | '\n' | '\r' | '\f')+ {$channel = HIDDEN;};