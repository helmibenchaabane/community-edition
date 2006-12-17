/*
 * Copyright (C) 2005 Alfresco, Inc.
 *
 * Licensed under the Mozilla Public License version 1.1 
 * with a permitted attribution clause. You may obtain a
 * copy of the License at
 *
 *   http://www.alfresco.org/legal/license.txt
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific
 * language governing permissions and limitations under the
 * License.
 */
package org.alfresco.web.action.evaluator;

import javax.faces.context.FacesContext;

import org.alfresco.service.ServiceRegistry;
import org.alfresco.service.cmr.repository.Path;
import org.alfresco.service.cmr.security.AccessStatus;
import org.alfresco.service.cmr.security.PermissionService;
import org.alfresco.web.action.ActionEvaluator;
import org.alfresco.web.app.Application;
import org.alfresco.web.app.servlet.FacesHelper;
import org.alfresco.web.bean.NavigationBean;
import org.alfresco.web.bean.repository.Node;
import org.alfresco.web.bean.repository.Repository;

/**
 * UI Action Evaluator - Create Web Form in the Forms DataDictionary folder
 * 
 * @author Kevin Roast
 */
public class CreateFormEvaluator implements ActionEvaluator
{
   /**
    * @see org.alfresco.web.action.ActionEvaluator#evaluate(org.alfresco.web.bean.repository.Node)
    */
   public boolean evaluate(Node node)
   {
      final FacesContext fc = FacesContext.getCurrentInstance();
      final ServiceRegistry services = Repository.getServiceRegistry(fc);
      final NavigationBean navigator = (NavigationBean)FacesHelper.getManagedBean(fc, NavigationBean.BEAN_NAME);
      // get the path to the current name - compare last element with the Website folder assoc name
      final Path path = services.getNodeService().getPath(navigator.getCurrentNode().getNodeRef());
      final Path.Element element = path.get(path.size() - 1);
      final String endPath = element.getPrefixedString(services.getNamespaceService());
      // check we have the permission to create nodes in that Website folder
      return (Application.getContentFormsFolderName(fc).equals(endPath) &&
              navigator.getCurrentNode().hasPermission(PermissionService.ADD_CHILDREN));
   }
}
