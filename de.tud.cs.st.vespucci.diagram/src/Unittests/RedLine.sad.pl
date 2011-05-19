%------
% Prolog based representation of the Vespucci architecture diagram: C:/files/uni/kurse/Bachelorpraktikum/ChecklistenProjekt/RedLine.sad
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

% Date <29/03/2011 23:54:23>.
%------

%------
%ensemble(File, Name, Query, SubEnsembles) :- Definition of an ensemble.
%	File - The simple file name in which the ensemble is defined. (e.g., 'Flashcards.sad')
%	Name - Name of the ensemble
%	Query - Query that determines which source elements belong to the ensemble
%	SubEnsembles - List of all sub ensembles of this ensemble.
%------
ensemble('RedLine.sad', 'Ebene1', [], (derived), ['Ebene2']).
ensemble('RedLine.sad', 'Ebene2', [], (derived), ['Ebene3']).
ensemble('RedLine.sad', 'Ebene3', [], (empty), []).
ensemble('RedLine.sad', 'Layer1', [], (derived), ['Layer2a', 'Layer2b']).
ensemble('RedLine.sad', 'Layer2a', [], (empty), []).
ensemble('RedLine.sad', 'Layer2b', [], (empty), []).

%------
%DEPENDENCY(File, ID, SourceE, TargetE, Type) :- Definition of a dependency between two ensembles.
%	DEPENDENCY - The type of the dependency. Possible values: outgoing, incoming, expected, not_allowed
%	File - The simple file name in which the dependency is defined. (e.g., 'Flashcards.sad')
%	ID - An ID identifying the dependency
%	SourceE - The source ensemble
%	TargetE - The target ensemble
%	Relation classifier - Kinds of uses-relation between source and target ensemble (all, field_access, method_call,...)
%------
incoming('RedLine.sad', 1, 'Ebene3', [], 'Layer2a', [], [all]).
outgoing('RedLine.sad', 1, 'Ebene3', [], 'Layer2a', [], [all]).
outgoing('RedLine.sad', 2, 'Ebene3', [], 'Ebene1', [], [all]).
outgoing('RedLine.sad', 3, 'Ebene3', [], 'Layer1', [], [all]).
outgoing('RedLine.sad', 4, 'Ebene2', [], 'Layer2b', [], [all]).
outgoing('RedLine.sad', 5, 'Layer2a', [], 'Layer2b', [], [all]).
not_allowed('RedLine.sad', 6, 'Layer2b', [], 'Layer1', [], [all]).
