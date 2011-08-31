%------
% Prolog based representation of the Vespucci architecture diagram: C:/files/uni/kurse/Bachelorpraktikum/ChecklistenProjekt/DiagramTest.sad
% Created by Vespucci, Technische Universitiät Darmstadt, Department of Computer Science
% www.opal-project.de

:- multifile ensemble/5.
:- multifile abstract_ensemble/5.
:- multifile outgoing/7.
:- multifile incoming/7.
:- multifile not_allowed/7.
:- multifile expected/7.
:- discontiguous ensemble/5.
:- discontiguous abstract_ensemble/5.
:- discontiguous outgoing/7.
:- discontiguous incoming/7.
:- discontiguous not_allowed/7.
:- discontiguous expected/7.

% Date <29/03/2011 15:52:43>.
%------

%------
%ensemble(File, Name, Query, SubEnsembles) :- Definition of an ensemble.
%	File - The simple file name in which the ensemble is defined. (e.g., 'Flashcards.sad')
%	Name - Name of the ensemble
%	Query - Query that determines which source elements belong to the ensemble
%	SubEnsembles - List of all sub ensembles of this ensemble.
%------
ensemble('DiagramTest.sad', 'Behälter1:', [], (derived), ['Behälter2:']).
ensemble('DiagramTest.sad', 'Behälter2:', [], (package('de.tud.cs.se.flashcards.model')
 ), []).
ensemble('DiagramTest.sad', 'Verschieben2:', [], (derived), ['mitVerschieben1:']).
ensemble('DiagramTest.sad', 'mitVerschieben1:', [], (package('de.tud.cs.se.flashcards.model.learning') ), []).
ensemble('DiagramTest.sad', 'Store.java', [], (class_with_members('de.tud.cs.se.flashcards.persistence','de.tud.cs.se.flashcards.persistence.Store') ), []).
ensemble('DiagramTest.sad',(empty),empty,[]).

%------
%DEPENDENCY(File, ID, SourceE, TargetE, Type) :- Definition of a dependency between two ensembles.
%	DEPENDENCY - The type of the dependency. Possible values: outgoing, incoming, expected, not_allowed
%	File - The simple file name in which the dependency is defined. (e.g., 'Flashcards.sad')
%	ID - An ID identifying the dependency
%	SourceE - The source ensemble
%	TargetE - The target ensemble
%	Relation classifier - Kinds of uses-relation between source and target ensemble (all, field_access, method_call,...)
%------
outgoing('DiagramTest.sad', 1, 'mitVerschieben1:', [], 'Store.java', [], [all]).
outgoing('DiagramTest.sad', 2, 'Store.java', [], empty, [], [all]).
