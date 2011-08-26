%------
% Prolog based representation of the Vespucci architecture diagram: C:/Users/dome/UNI/SS 11/BP/bp-workspace/vespucci/de.tud.cs.st.vespucci.diagram/Model/DiagramConverter.sad
% Created by Vespucci, Technische Universität Darmstadt, Department of Computer Science
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

% Date <25/08/2011 14:32:40>.
%------

%------
%ensemble(File, Name, Query, SubEnsembles) :- Definition of an ensemble.
%	File - The simple file name in which the ensemble is defined. (e.g., 'Flashcards.sad')
%	Name - Name of the ensemble
%NEW: EnsembleParameter - Parameters for the ensemble
%	Query - Query that determines which source elements belong to the ensemble
%	SubEnsembles - List of all sub ensembles of this ensemble.
%------
ensemble('DiagramConverter.sad', 'Lib', [], (empty), []).
ensemble('DiagramConverter.sad', '"GUI"-Vespucci Diagram', [], (derived), ['Handle Save/Convert', 'Rest', 'DiagramConverter']).
ensemble('DiagramConverter.sad', 'Handle Save/Convert', [], (class_with_members('de.tud.cs.st.vespucci.diagram.handler','GeneratePrologFacts') or class_with_members('de.tud.cs.st.vespucci.vespucci_model.diagram.part','VespucciDiagramEditor')
 ), []).
ensemble('DiagramConverter.sad', 'Rest', [], (class_with_members('de.tud.cs.st.vespucci.vespucci_model.diagram.part','VespucciNodeDescriptor')
  or class_with_members('de.tud.cs.st.vespucci.vespucci_model.diagram.part','VespucciPaletteFactory')
  or class_with_members('de.tud.cs.st.vespucci.vespucci_model.diagram.part','VespucciInitDiagramFileAction')
  or class_with_members('de.tud.cs.st.vespucci.vespucci_model.diagram.part','VespucciDiagramUpdateCommand')
  or package('de.tud.cs.st.vespucci.vespucci_model.diagram.sheet')
  or package('de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts')
  or class_with_members('de.tud.cs.st.vespucci.vespucci_model.diagram.part','VespucciVisualIDRegistry')
  or class_with_members('de.tud.cs.st.vespucci.vespucci_model.diagram.part','VespucciDiagramEditorUtil')
  or class_with_members('de.tud.cs.st.vespucci.vespucci_model.diagram.part','DeleteElementAction')
  or class_with_members('de.tud.cs.st.vespucci.vespucci_model.diagram.part','VespucciDiagramUpdater')
  or package('de.tud.cs.st.vespucci.vespucci_model.diagram.navigator')
  or class_with_members('de.tud.cs.st.vespucci.vespucci_model.diagram.part','VespucciDiagramActionBarContributor')
  or class_with_members('de.tud.cs.st.vespucci.vespucci_model.diagram.part','VespucciCreationWizardPage')
  or class_with_members('de.tud.cs.st.vespucci.vespucci_model.diagram.part','VespucciDocumentProvider')
  or class_with_members('de.tud.cs.st.vespucci.vespucci_model.diagram.part','VespucciMatchingStrategy')
  or package('de.tud.cs.st.vespucci.diagram.dnd')
  or package('de.tud.cs.st.vespucci.vespucci_model.diagram.preferences')
  or class_with_members('de.tud.cs.st.vespucci.vespucci_model.diagram.part','Messages')
  or class_with_members('de.tud.cs.st.vespucci.vespucci_model.diagram.part','VespucciCreationWizard')
  or class_with_members('de.tud.cs.st.vespucci.vespucci_model.diagram.part','VespucciUriEditorInputTester')
  or class_with_members('de.tud.cs.st.vespucci.vespucci_model.diagram.part','ModelElementSelectionPage')
  or package('de.tud.cs.st.vespucci.vespucci_model.diagram.expressions')
  or package('de.tud.cs.st.vespucci.vespucci_model.diagram.edit.commands')
  or class_with_members('de.tud.cs.st.vespucci.vespucci_model.diagram.part','VespucciContainerEditPart')
  or class_with_members('de.tud.cs.st.vespucci.vespucci_model.diagram.part','VespucciLinkDescriptor')
  or class_with_members('de.tud.cs.st.vespucci.vespucci_model.diagram.part','VespucciNewDiagramFileWizard')
  or package('de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.outline')
  or class_with_members('de.tud.cs.st.vespucci.vespucci_model.diagram.part','LoadResourceAction')
  or package('de.tud.cs.st.vespucci.diagram.dnd.JavaType')
  or package('de.tud.cs.st.vespucci.vespucci_model.diagram.parsers')
  or package('de.tud.cs.st.vespucci.vespucci_model.diagram.providers')
  or package('de.tud.cs.st.vespucci.vespucci_model.diagram.edit.helpers')
  or class_with_members('de.tud.cs.st.vespucci.vespucci_model.diagram.part','DiagramEditorContextMenuProvider')
  or package('de.tud.cs.st.vespucci.diagram.supports')
  or package('de.tud.cs.st.vespucci.vespucci_model.diagram.edit.policies')
  or class_with_members('de.tud.cs.st.vespucci.vespucci_model.diagram.part','ValidateAction')
  or class_with_members('de.tud.cs.st.vespucci.vespucci_model.diagram.part','VespucciDiagramEditorPlugin')), []).
ensemble('DiagramConverter.sad', 'DiagramConverter', [], (package('de.tud.cs.st.vespucci.diagram.converter')), []).
ensemble('DiagramConverter.sad', 'MetaModel', [], (derived), ['Interfaces', 'Impl']).
ensemble('DiagramConverter.sad', 'Interfaces', [], (package('de.tud.cs.st.vespucci.vespucci_model')), []).
ensemble('DiagramConverter.sad', 'Impl', [], (package('de.tud.cs.st.vespucci.vespucci_model.impl')), []).

%------
%DEPENDENCY(File, ID, SourceE, TargetE, Type) :- Definition of a dependency between two ensembles.
%	DEPENDENCY - The type of the dependency. Possible values: outgoing, incoming, expected, not_allowed
%	File - The simple file name in which the dependency is defined. (e.g., 'Flashcards.sad')
%	ID - An ID identifying the dependency
%	SourceE - The source ensemble
%	TargetE - The target ensemble
%NEW: Parameters from Source and Target
%	Relation classifier - Kinds of uses-relation between source and target ensemble (all, field_access, method_call,...)
%------
outgoing('DiagramConverter.sad', 1, 'Handle Save/Convert', [], 'DiagramConverter', [], all).
outgoing('DiagramConverter.sad', 2, 'Handle Save/Convert', [], 'Rest', [], all).
outgoing('DiagramConverter.sad', 3, 'Handle Save/Convert', [], 'Interfaces', [], all).
outgoing('DiagramConverter.sad', 4, 'Rest', [], 'MetaModel', [], all).
outgoing('DiagramConverter.sad', 5, 'Rest', [], 'Handle Save/Convert', [], all).
outgoing('DiagramConverter.sad', 6, 'DiagramConverter', [], 'Lib', [], all).
outgoing('DiagramConverter.sad', 7, 'DiagramConverter', [], 'Interfaces', [], all).
