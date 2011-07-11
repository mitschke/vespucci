/*
 *  License (BSD Style License):
 *   Copyright (c) 2010
 *   Author Tam-Minh Nguyen
 *   Software Engineering
 *   Department of Computer Science
 *   Technische Universität Darmstadt
 *   All rights reserved.
 * 
 *   Redistribution and use in source and binary forms, with or without
 *   modification, are permitted provided that the following conditions are met:
 * 
 *   - Redistributions of source code must retain the above copyright notice,
 *     this list of conditions and the following disclaimer.
 *   - Redistributions in binary form must reproduce the above copyright notice,
 *     this list of conditions and the following disclaimer in the documentation
 *     and/or other materials provided with the distribution.
 *   - Neither the name of the Software Engineering Group or Technische 
 *     Universität Darmstadt nor the names of its contributors may be used to 
 *     endorse or promote products derived from this software without specific 
 *     prior written permission.
 * 
 *   THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 *   AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 *   IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 *   ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 *   LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *   CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 *   SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 *   INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 *   CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 *   ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 *   POSSIBILITY OF SUCH DAMAGE.
 */
package de.tud.cs.st.vespucci.vespucci_model.diagram.edit.policies;

import java.util.Collections;
import java.util.Iterator;
import java.util.Map;

import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.UnexecutableCommand;
import org.eclipse.gef.requests.ReconnectRequest;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.common.core.command.ICompositeCommand;
import org.eclipse.gmf.runtime.diagram.core.commands.DeleteCommand;
import org.eclipse.gmf.runtime.diagram.ui.commands.CommandProxy;
import org.eclipse.gmf.runtime.diagram.ui.commands.ICommandProxy;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.SemanticEditPolicy;
import org.eclipse.gmf.runtime.emf.commands.core.command.CompositeTransactionalCommand;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.requests.ConfigureRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateRelationshipRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyReferenceRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.DuplicateElementsRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.GetEditContextRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.IEditCommandRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.MoveRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.ReorientReferenceRelationshipRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.ReorientRelationshipRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.SetRequest;
import org.eclipse.gmf.runtime.notation.View;

/**
 * @generated
 */
public class VespucciBaseItemSemanticEditPolicy extends SemanticEditPolicy {

	/**
	 * Extended request data key to hold editpart visual id.
	 * @generated
	 */
	public static final String VISUAL_ID_KEY = "visual_id"; //$NON-NLS-1$

	/**
	 * @generated
	 */
	private final IElementType myElementType;

	/**
	 * @generated
	 */
	protected VespucciBaseItemSemanticEditPolicy(IElementType elementType) {
		myElementType = elementType;
	}

	/**
	 * Extended request data key to hold editpart visual id.
	 * Add visual id of edited editpart to extended data of the request
	 * so command switch can decide what kind of diagram element is being edited.
	 * It is done in those cases when it's not possible to deduce diagram
	 * element kind from domain element.
	 * 
	 * @generated
	 */
	public Command getCommand(Request request) {
		if (request instanceof ReconnectRequest) {
			Object view = ((ReconnectRequest) request).getConnectionEditPart()
					.getModel();
			if (view instanceof View) {
				Integer id = new Integer(
						de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciVisualIDRegistry
								.getVisualID((View) view));
				request.getExtendedData().put(VISUAL_ID_KEY, id);
			}
		}
		return super.getCommand(request);
	}

	/**
	 * Returns visual id from request parameters.
	 * @generated
	 */
	protected int getVisualID(IEditCommandRequest request) {
		Object id = request.getParameter(VISUAL_ID_KEY);
		return id instanceof Integer ? ((Integer) id).intValue() : -1;
	}

	/**
	 * @generated
	 */
	protected Command getSemanticCommand(IEditCommandRequest request) {
		IEditCommandRequest completedRequest = completeRequest(request);
		Command semanticCommand = getSemanticCommandSwitch(completedRequest);
		semanticCommand = getEditHelperCommand(completedRequest,
				semanticCommand);
		if (completedRequest instanceof DestroyRequest) {
			DestroyRequest destroyRequest = (DestroyRequest) completedRequest;
			return shouldProceed(destroyRequest) ? addDeleteViewCommand(
					semanticCommand, destroyRequest) : null;
		}
		return semanticCommand;
	}

	/**
	 * @generated
	 */
	protected Command addDeleteViewCommand(Command mainCommand,
			DestroyRequest completedRequest) {
		Command deleteViewCommand = getGEFWrapper(new DeleteCommand(
				getEditingDomain(), (View) getHost().getModel()));
		return mainCommand == null ? deleteViewCommand : mainCommand
				.chain(deleteViewCommand);
	}

	/**
	 * @generated
	 */
	private Command getEditHelperCommand(IEditCommandRequest request,
			Command editPolicyCommand) {
		if (editPolicyCommand != null) {
			ICommand command = editPolicyCommand instanceof ICommandProxy ? ((ICommandProxy) editPolicyCommand)
					.getICommand() : new CommandProxy(editPolicyCommand);
			request.setParameter(
					de.tud.cs.st.vespucci.vespucci_model.diagram.edit.helpers.VespucciBaseEditHelper.EDIT_POLICY_COMMAND,
					command);
		}
		IElementType requestContextElementType = getContextElementType(request);
		request.setParameter(
				de.tud.cs.st.vespucci.vespucci_model.diagram.edit.helpers.VespucciBaseEditHelper.CONTEXT_ELEMENT_TYPE,
				requestContextElementType);
		ICommand command = requestContextElementType.getEditCommand(request);
		request.setParameter(
				de.tud.cs.st.vespucci.vespucci_model.diagram.edit.helpers.VespucciBaseEditHelper.EDIT_POLICY_COMMAND,
				null);
		request.setParameter(
				de.tud.cs.st.vespucci.vespucci_model.diagram.edit.helpers.VespucciBaseEditHelper.CONTEXT_ELEMENT_TYPE,
				null);
		if (command != null) {
			if (!(command instanceof CompositeTransactionalCommand)) {
				command = new CompositeTransactionalCommand(getEditingDomain(),
						command.getLabel()).compose(command);
			}
			return new ICommandProxy(command);
		}
		return editPolicyCommand;
	}

	/**
	 * @generated
	 */
	private IElementType getContextElementType(IEditCommandRequest request) {
		IElementType requestContextElementType = de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes
				.getElementType(getVisualID(request));
		return requestContextElementType != null ? requestContextElementType
				: myElementType;
	}

	/**
	 * @generated
	 */
	protected Command getSemanticCommandSwitch(IEditCommandRequest req) {
		if (req instanceof CreateRelationshipRequest) {
			return getCreateRelationshipCommand((CreateRelationshipRequest) req);
		} else if (req instanceof CreateElementRequest) {
			return getCreateCommand((CreateElementRequest) req);
		} else if (req instanceof ConfigureRequest) {
			return getConfigureCommand((ConfigureRequest) req);
		} else if (req instanceof DestroyElementRequest) {
			return getDestroyElementCommand((DestroyElementRequest) req);
		} else if (req instanceof DestroyReferenceRequest) {
			return getDestroyReferenceCommand((DestroyReferenceRequest) req);
		} else if (req instanceof DuplicateElementsRequest) {
			return getDuplicateCommand((DuplicateElementsRequest) req);
		} else if (req instanceof GetEditContextRequest) {
			return getEditContextCommand((GetEditContextRequest) req);
		} else if (req instanceof MoveRequest) {
			return getMoveCommand((MoveRequest) req);
		} else if (req instanceof ReorientReferenceRelationshipRequest) {
			return getReorientReferenceRelationshipCommand((ReorientReferenceRelationshipRequest) req);
		} else if (req instanceof ReorientRelationshipRequest) {
			return getReorientRelationshipCommand((ReorientRelationshipRequest) req);
		} else if (req instanceof SetRequest) {
			return getSetCommand((SetRequest) req);
		}
		return null;
	}

	/**
	 * @generated
	 */
	protected Command getConfigureCommand(ConfigureRequest req) {
		return null;
	}

	/**
	 * @generated
	 */
	protected Command getCreateRelationshipCommand(CreateRelationshipRequest req) {
		return null;
	}

	/**
	 * @generated
	 */
	protected Command getCreateCommand(CreateElementRequest req) {
		return null;
	}

	/**
	 * @generated
	 */
	protected Command getSetCommand(SetRequest req) {
		return null;
	}

	/**
	 * @generated
	 */
	protected Command getEditContextCommand(GetEditContextRequest req) {
		return null;
	}

	/**
	 * @generated
	 */
	protected Command getDestroyElementCommand(DestroyElementRequest req) {
		return null;
	}

	/**
	 * @generated
	 */
	protected Command getDestroyReferenceCommand(DestroyReferenceRequest req) {
		return null;
	}

	/**
	 * @generated
	 */
	protected Command getDuplicateCommand(DuplicateElementsRequest req) {
		return null;
	}

	/**
	 * @generated
	 */
	protected Command getMoveCommand(MoveRequest req) {
		return null;
	}

	/**
	 * @generated
	 */
	protected Command getReorientReferenceRelationshipCommand(
			ReorientReferenceRelationshipRequest req) {
		return UnexecutableCommand.INSTANCE;
	}

	/**
	 * @generated
	 */
	protected Command getReorientRelationshipCommand(
			ReorientRelationshipRequest req) {
		return UnexecutableCommand.INSTANCE;
	}

	/**
	 * @generated
	 */
	protected final Command getGEFWrapper(ICommand cmd) {
		return new ICommandProxy(cmd);
	}

	/**
	 * Returns editing domain from the host edit part.
	 * @generated
	 */
	protected TransactionalEditingDomain getEditingDomain() {
		return ((IGraphicalEditPart) getHost()).getEditingDomain();
	}

	/**
	 * Clean all shortcuts to the host element from the same diagram
	 * @generated
	 */
	protected void addDestroyShortcutsCommand(ICompositeCommand cmd, View view) {
		assert view.getEAnnotation("Shortcut") == null; //$NON-NLS-1$
		for (Iterator it = view.getDiagram().getChildren().iterator(); it
				.hasNext();) {
			View nextView = (View) it.next();
			if (nextView.getEAnnotation("Shortcut") == null || !nextView.isSetElement() || nextView.getElement() != view.getElement()) { //$NON-NLS-1$
				continue;
			}
			cmd.add(new DeleteCommand(getEditingDomain(), nextView));
		}
	}

	/**
	 * @generated
	 */
	public static LinkConstraints getLinkConstraints() {
		LinkConstraints cached = de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciDiagramEditorPlugin
				.getInstance().getLinkConstraints();
		if (cached == null) {
			de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciDiagramEditorPlugin
					.getInstance().setLinkConstraints(
							cached = new LinkConstraints());
		}
		return cached;
	}

	/**
	 * @generated
	 */
	public static class LinkConstraints {

		/**
		 * @generated
		 */
		LinkConstraints() {
			// use static method #getLinkConstraints() to access instance
		}

		/**
		 * @generated
		 */
		public boolean canCreateIncoming_4005(
				de.tud.cs.st.vespucci.vespucci_model.Shape container,
				de.tud.cs.st.vespucci.vespucci_model.Shape source,
				de.tud.cs.st.vespucci.vespucci_model.Shape target) {
			return canExistIncoming_4005(container, null, source, target);
		}

		/**
		 * @generated
		 */
		public boolean canCreateOutgoing_4003(
				de.tud.cs.st.vespucci.vespucci_model.Shape container,
				de.tud.cs.st.vespucci.vespucci_model.Shape source,
				de.tud.cs.st.vespucci.vespucci_model.Shape target) {
			return canExistOutgoing_4003(container, null, source, target);
		}

		/**
		 * @generated
		 */
		public boolean canCreateInAndOut_4001(
				de.tud.cs.st.vespucci.vespucci_model.Shape container,
				de.tud.cs.st.vespucci.vespucci_model.Shape source,
				de.tud.cs.st.vespucci.vespucci_model.Shape target) {
			return canExistInAndOut_4001(container, null, source, target);
		}

		/**
		 * @generated
		 */
		public boolean canCreateNotAllowed_4004(
				de.tud.cs.st.vespucci.vespucci_model.Shape container,
				de.tud.cs.st.vespucci.vespucci_model.Shape source,
				de.tud.cs.st.vespucci.vespucci_model.Shape target) {
			return canExistNotAllowed_4004(container, null, source, target);
		}

		/**
		 * @generated
		 */
		public boolean canCreateExpected_4002(
				de.tud.cs.st.vespucci.vespucci_model.Shape container,
				de.tud.cs.st.vespucci.vespucci_model.Shape source,
				de.tud.cs.st.vespucci.vespucci_model.Shape target) {
			return canExistExpected_4002(container, null, source, target);
		}

		/**
		 * @generated
		 */
		public boolean canExistIncoming_4005(
				de.tud.cs.st.vespucci.vespucci_model.Shape container,
				de.tud.cs.st.vespucci.vespucci_model.Incoming linkInstance,
				de.tud.cs.st.vespucci.vespucci_model.Shape source,
				de.tud.cs.st.vespucci.vespucci_model.Shape target) {
			try {
				if (source == null) {
					return true;
				} else {
					Map<String, EClassifier> env = Collections
							.<String, EClassifier> singletonMap(
									"oppositeEnd", de.tud.cs.st.vespucci.vespucci_model.Vespucci_modelPackage.eINSTANCE.getShape()); //$NON-NLS-1$
					Object sourceVal = de.tud.cs.st.vespucci.vespucci_model.diagram.expressions.VespucciOCLFactory
							.getExpression(
									0,
									de.tud.cs.st.vespucci.vespucci_model.Vespucci_modelPackage.eINSTANCE
											.getShape(), env).evaluate(
									source,
									Collections.singletonMap(
											"oppositeEnd", target)); //$NON-NLS-1$
					if (false == sourceVal instanceof Boolean
							|| !((Boolean) sourceVal).booleanValue()) {
						return false;
					} // else fall-through
				}
				if (target == null) {
					return true;
				} else {
					Map<String, EClassifier> env = Collections
							.<String, EClassifier> singletonMap(
									"oppositeEnd", de.tud.cs.st.vespucci.vespucci_model.Vespucci_modelPackage.eINSTANCE.getShape()); //$NON-NLS-1$
					Object targetVal = de.tud.cs.st.vespucci.vespucci_model.diagram.expressions.VespucciOCLFactory
							.getExpression(
									1,
									de.tud.cs.st.vespucci.vespucci_model.Vespucci_modelPackage.eINSTANCE
											.getShape(), env).evaluate(
									target,
									Collections.singletonMap(
											"oppositeEnd", source)); //$NON-NLS-1$
					if (false == targetVal instanceof Boolean
							|| !((Boolean) targetVal).booleanValue()) {
						return false;
					} // else fall-through
				}
				return true;
			} catch (Exception e) {
				de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciDiagramEditorPlugin
						.getInstance().logError(
								"Link constraint evaluation error", e); //$NON-NLS-1$
				return false;
			}
		}

		/**
		 * @generated
		 */
		public boolean canExistOutgoing_4003(
				de.tud.cs.st.vespucci.vespucci_model.Shape container,
				de.tud.cs.st.vespucci.vespucci_model.Outgoing linkInstance,
				de.tud.cs.st.vespucci.vespucci_model.Shape source,
				de.tud.cs.st.vespucci.vespucci_model.Shape target) {
			try {
				if (source == null) {
					return true;
				} else {
					Map<String, EClassifier> env = Collections
							.<String, EClassifier> singletonMap(
									"oppositeEnd", de.tud.cs.st.vespucci.vespucci_model.Vespucci_modelPackage.eINSTANCE.getShape()); //$NON-NLS-1$
					Object sourceVal = de.tud.cs.st.vespucci.vespucci_model.diagram.expressions.VespucciOCLFactory
							.getExpression(
									2,
									de.tud.cs.st.vespucci.vespucci_model.Vespucci_modelPackage.eINSTANCE
											.getShape(), env).evaluate(
									source,
									Collections.singletonMap(
											"oppositeEnd", target)); //$NON-NLS-1$
					if (false == sourceVal instanceof Boolean
							|| !((Boolean) sourceVal).booleanValue()) {
						return false;
					} // else fall-through
				}
				return true;
			} catch (Exception e) {
				de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciDiagramEditorPlugin
						.getInstance().logError(
								"Link constraint evaluation error", e); //$NON-NLS-1$
				return false;
			}
		}

		/**
		 * @generated
		 */
		public boolean canExistInAndOut_4001(
				de.tud.cs.st.vespucci.vespucci_model.Shape container,
				de.tud.cs.st.vespucci.vespucci_model.InAndOut linkInstance,
				de.tud.cs.st.vespucci.vespucci_model.Shape source,
				de.tud.cs.st.vespucci.vespucci_model.Shape target) {
			try {
				if (source == null) {
					return true;
				} else {
					Map<String, EClassifier> env = Collections
							.<String, EClassifier> singletonMap(
									"oppositeEnd", de.tud.cs.st.vespucci.vespucci_model.Vespucci_modelPackage.eINSTANCE.getShape()); //$NON-NLS-1$
					Object sourceVal = de.tud.cs.st.vespucci.vespucci_model.diagram.expressions.VespucciOCLFactory
							.getExpression(
									3,
									de.tud.cs.st.vespucci.vespucci_model.Vespucci_modelPackage.eINSTANCE
											.getShape(), env).evaluate(
									source,
									Collections.singletonMap(
											"oppositeEnd", target)); //$NON-NLS-1$
					if (false == sourceVal instanceof Boolean
							|| !((Boolean) sourceVal).booleanValue()) {
						return false;
					} // else fall-through
				}
				if (target == null) {
					return true;
				} else {
					Map<String, EClassifier> env = Collections
							.<String, EClassifier> singletonMap(
									"oppositeEnd", de.tud.cs.st.vespucci.vespucci_model.Vespucci_modelPackage.eINSTANCE.getShape()); //$NON-NLS-1$
					Object targetVal = de.tud.cs.st.vespucci.vespucci_model.diagram.expressions.VespucciOCLFactory
							.getExpression(
									4,
									de.tud.cs.st.vespucci.vespucci_model.Vespucci_modelPackage.eINSTANCE
											.getShape(), env).evaluate(
									target,
									Collections.singletonMap(
											"oppositeEnd", source)); //$NON-NLS-1$
					if (false == targetVal instanceof Boolean
							|| !((Boolean) targetVal).booleanValue()) {
						return false;
					} // else fall-through
				}
				return true;
			} catch (Exception e) {
				de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciDiagramEditorPlugin
						.getInstance().logError(
								"Link constraint evaluation error", e); //$NON-NLS-1$
				return false;
			}
		}

		/**
		 * @generated
		 */
		public boolean canExistNotAllowed_4004(
				de.tud.cs.st.vespucci.vespucci_model.Shape container,
				de.tud.cs.st.vespucci.vespucci_model.NotAllowed linkInstance,
				de.tud.cs.st.vespucci.vespucci_model.Shape source,
				de.tud.cs.st.vespucci.vespucci_model.Shape target) {
			try {
				if (source == null) {
					return true;
				} else {
					Map<String, EClassifier> env = Collections
							.<String, EClassifier> singletonMap(
									"oppositeEnd", de.tud.cs.st.vespucci.vespucci_model.Vespucci_modelPackage.eINSTANCE.getShape()); //$NON-NLS-1$
					Object sourceVal = de.tud.cs.st.vespucci.vespucci_model.diagram.expressions.VespucciOCLFactory
							.getExpression(
									5,
									de.tud.cs.st.vespucci.vespucci_model.Vespucci_modelPackage.eINSTANCE
											.getShape(), env).evaluate(
									source,
									Collections.singletonMap(
											"oppositeEnd", target)); //$NON-NLS-1$
					if (false == sourceVal instanceof Boolean
							|| !((Boolean) sourceVal).booleanValue()) {
						return false;
					} // else fall-through
				}
				if (target == null) {
					return true;
				} else {
					Map<String, EClassifier> env = Collections
							.<String, EClassifier> singletonMap(
									"oppositeEnd", de.tud.cs.st.vespucci.vespucci_model.Vespucci_modelPackage.eINSTANCE.getShape()); //$NON-NLS-1$
					Object targetVal = de.tud.cs.st.vespucci.vespucci_model.diagram.expressions.VespucciOCLFactory
							.getExpression(
									6,
									de.tud.cs.st.vespucci.vespucci_model.Vespucci_modelPackage.eINSTANCE
											.getShape(), env).evaluate(
									target,
									Collections.singletonMap(
											"oppositeEnd", source)); //$NON-NLS-1$
					if (false == targetVal instanceof Boolean
							|| !((Boolean) targetVal).booleanValue()) {
						return false;
					} // else fall-through
				}
				return true;
			} catch (Exception e) {
				de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciDiagramEditorPlugin
						.getInstance().logError(
								"Link constraint evaluation error", e); //$NON-NLS-1$
				return false;
			}
		}

		/**
		 * @generated
		 */
		public boolean canExistExpected_4002(
				de.tud.cs.st.vespucci.vespucci_model.Shape container,
				de.tud.cs.st.vespucci.vespucci_model.Expected linkInstance,
				de.tud.cs.st.vespucci.vespucci_model.Shape source,
				de.tud.cs.st.vespucci.vespucci_model.Shape target) {
			try {
				if (source == null) {
					return true;
				} else {
					Map<String, EClassifier> env = Collections
							.<String, EClassifier> singletonMap(
									"oppositeEnd", de.tud.cs.st.vespucci.vespucci_model.Vespucci_modelPackage.eINSTANCE.getShape()); //$NON-NLS-1$
					Object sourceVal = de.tud.cs.st.vespucci.vespucci_model.diagram.expressions.VespucciOCLFactory
							.getExpression(
									7,
									de.tud.cs.st.vespucci.vespucci_model.Vespucci_modelPackage.eINSTANCE
											.getShape(), env).evaluate(
									source,
									Collections.singletonMap(
											"oppositeEnd", target)); //$NON-NLS-1$
					if (false == sourceVal instanceof Boolean
							|| !((Boolean) sourceVal).booleanValue()) {
						return false;
					} // else fall-through
				}
				if (target == null) {
					return true;
				} else {
					Map<String, EClassifier> env = Collections
							.<String, EClassifier> singletonMap(
									"oppositeEnd", de.tud.cs.st.vespucci.vespucci_model.Vespucci_modelPackage.eINSTANCE.getShape()); //$NON-NLS-1$
					Object targetVal = de.tud.cs.st.vespucci.vespucci_model.diagram.expressions.VespucciOCLFactory
							.getExpression(
									8,
									de.tud.cs.st.vespucci.vespucci_model.Vespucci_modelPackage.eINSTANCE
											.getShape(), env).evaluate(
									target,
									Collections.singletonMap(
											"oppositeEnd", source)); //$NON-NLS-1$
					if (false == targetVal instanceof Boolean
							|| !((Boolean) targetVal).booleanValue()) {
						return false;
					} // else fall-through
				}
				return true;
			} catch (Exception e) {
				de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciDiagramEditorPlugin
						.getInstance().logError(
								"Link constraint evaluation error", e); //$NON-NLS-1$
				return false;
			}
		}
	}

}
