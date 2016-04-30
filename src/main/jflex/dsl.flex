package seng302;

import java_cup.runtime.*;
import java_cup.runtime.ComplexSymbolFactory.Location;


%%

%class DslLexer


%line
%column
%unicode
%caseless
/* Uncomment for debugging info.
%debug
*/

%cupsym DslSymbol
%cup

   
/* Declarations
 * The contents of this block is copied verbatim into the Lexer class, this
 * provides the ability to create member variable and methods to use in the
 * action blocks for rules.
*/
%{
   class MyComplexSymbol extends ComplexSymbolFactory.ComplexSymbol {

      MyComplexSymbol(String name, int type, Location left, Location right, Object val){
        super(name, type, left,right,val);
      }
      MyComplexSymbol(String name, int type, Location left, Location right){
        super(name, type, left,right);
      }

      public String toString(){
          return this.name;
      }
   }

   class MyComplexSymbolFactory extends ComplexSymbolFactory{
        public Symbol newSymbol(String name, int type, Location left, Location right){
            return new MyComplexSymbol(name, type, left, right);
        }

        public Symbol newSymbol(String name, int type, Location left, Location right, Object val){
            return new MyComplexSymbol(name, type, left, right, val);
        }

   }

   ComplexSymbolFactory csf = new MyComplexSymbolFactory();

   private Symbol symbol(String name, int type, Object value) {
       Location left = new Location(yyline+1,yycolumn+1,yychar);
       Location right= new Location(yyline+1,yycolumn+yylength(), yychar+yylength());
       return csf.newSymbol(name, type, left, right, value);
   }

   private Symbol symbol(String name, int type) {
       Location left = new Location(yyline+1,yycolumn+1,yychar);
       Location right= new Location(yyline+1,yycolumn+yylength(), yychar+yylength());
       return csf.newSymbol(name, type, left, right);
   }



    /* To create a new java_cup.runtime.Symbol with information about
       the current token, the token will have no value in this
       case. */
    private Symbol symbol(int type) {
        //System.err.println("Obtain token " + DslSymbol.terminalNames[type] + " \"" + yytext() + "\"" );
        return new Symbol(type, yyline, yycolumn);
    }
    
    /* Also creates a new java_cup.runtime.Symbol with information
       about the current token, but this object has a value. */
    private Symbol symbol(int type, Object value) {
        //System.err.println("Obtain token " + DslSymbol.terminalNames[type] + " \"" + yytext() + "\"" );
        return new Symbol(type, yyline, yycolumn, value);
    }


%}
   

/*
  Macro Declarations
*/

WhiteSpace = \p{Whitespace}
Number = \p{Digit}
Note = [A-G|a-g]([#|b|x]|(bb))?[0-8]?|[D-G|d-g]([#|b|x]|(bb))?[-1]?|[A-F|a-f]([#|b|x]|(bb))?[9]?|[C|c][#|x]?(-1)?|[G|g](b|bb)?[9]?
MidiNote = (0?[0-9]?[0-9]|1[01][0-9]|12[0-7])
Atom = [^\s|;]+
SemiColon = ";"
ScaleType = "major"
Direction = "updown"|"up"|"down"
PosNum = \p{Digit}+
Interval = ("unison"|"major second"|"major third"|"perfect fourth"|"perfect fifth"|"major sixth"|"major seventh"|"octave")
   
%%

/* Rules
   YYINTIAL means that they are only matched at the beginning e.g "help play" would only
   match help because play is not at the beginning.
*/

<YYINITIAL> {
    "help"             { return symbol(DslSymbol.COMMAND_HELP); }
    "note"             { return symbol(DslSymbol.COMMAND_NOTE); }
    "midi"             { return symbol(DslSymbol.COMMAND_MIDI); }
    "tempo"            { return symbol(DslSymbol.COMMAND_TEMPO); }
    "version"          { return symbol(DslSymbol.COMMAND_VERSION); }
    "semitone up"      { return symbol(DslSymbol.COMMAND_SEMITONE_UP);}
    "semitone down"    {return symbol(DslSymbol.COMMAND_SEMITONE_DOWN);}
    "scale"            {return symbol(DslSymbol.COMMAND_SCALE);}
    "midi scale"       {return symbol(DslSymbol.COMMAND_MIDI_SCALE);}
    "enharmonic higher"  { return symbol(DslSymbol.COMMAND_ENHARMONIC_HIGHER); }
    "enharmonic lower"   { return symbol(DslSymbol.COMMAND_ENHARMONIC_LOWER); }
    "force set tempo"  { return symbol(DslSymbol.COMMAND_FORCE_SET_TEMPO); }
    "simple enharmonic" { return symbol(DslSymbol.COMMAND_SIMPLE_ENHARMONIC); }
    "set tempo"        { return symbol(DslSymbol.COMMAND_SET_TEMPO);  }
    "play scale"        {return symbol(DslSymbol.COMMAND_PLAY_SCALE); }
    "play interval"     {return symbol(DslSymbol.COMMAND_PLAY_INTERVAL); }
    "play"             { return symbol(DslSymbol.COMMAND_PLAY_NOTE);    }
    "interval"          {return symbol(DslSymbol.COMMAND_INTERVAL); }
    "crotchet duration"    { return symbol(DslSymbol.COMMAND_CROTCHET_DURATION); }
    "meaning of"       { return symbol(DslSymbol.COMMAND_MUSICAL_TERM); }
    "add musical term"  {return symbol(DslSymbol.COMMAND_ADD_MUSICAL_TERM); }
    "all enharmonics"   {return symbol(DslSymbol.COMMAND_ALL_ENHARMONICS); }
    "undo"              {return symbol(DslSymbol.COMMAND_UNDO); }
    "redo"              {return symbol(DslSymbol.COMMAND_REDO); }
    {Note}              {return symbol(DslSymbol.NOTE, new String(yytext()));}
    {Number}           { return symbol(DslSymbol.NUMBER, new String(yytext())); }
    {MidiNote}          {return symbol(DslSymbol.MIDINOTE, new String(yytext())); }
    {ScaleType}         {return symbol(DslSymbol.SCALE_TYPE, new String(yytext()));}
    {Direction}         {return symbol(DslSymbol.DIRECTION, new String(yytext()));}
    {PosNum}            {return symbol(DslSymbol.POSNUM, new String(yytext()));}
    {Interval}          {return symbol(DslSymbol.INTERVAL, new String(yytext()));}
    {SemiColon}         {return symbol(DslSymbol.SEMIC);}
    {Atom}             { return symbol(DslSymbol.ATOM, new String(yytext()));}
    {WhiteSpace}       { /* Ignore whitespace */ }
}

/* Throw an exception if we have no matches */
[^]                    { throw new RuntimeException("Illegal character <"+yytext()+">"); }