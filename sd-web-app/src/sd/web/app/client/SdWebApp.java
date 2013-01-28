/*******************************************************************************
 * Copyright 2011 Google Inc. All Rights Reserved.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package sd.web.app.client;


import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.DecoratedStackPanel;
import com.google.gwt.user.client.ui.VerticalSplitPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.HorizontalSplitPanel;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.TextArea;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class SdWebApp implements EntryPoint {
	public void onModuleLoad() {
		RootLayoutPanel rootLayoutPanel = RootLayoutPanel.get();
		rootLayoutPanel.setSize("auto", "auto");
		rootLayoutPanel.setStyleName("button");

		LayoutPanel layoutPanel = new LayoutPanel();
		rootLayoutPanel.add(layoutPanel);

		VerticalPanel verticalPanel = new VerticalPanel();
		verticalPanel.setStyleName("body");
		verticalPanel
				.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		layoutPanel.add(verticalPanel);

		Image image = new Image("verisure.jpg");
		verticalPanel.add(image);
		image.setSize("266px", "161px");

		TabPanel tabPanel = new TabPanel();
		tabPanel.setAnimationEnabled(true);
		tabPanel.setStyleName("body");
		verticalPanel.add(tabPanel);
		tabPanel.setSize("auto", "auto");

		VerticalPanel receivePanel = new VerticalPanel();
		receivePanel
				.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		tabPanel.add(receivePanel, "Receive ASCII", false);
		receivePanel.setSize("5cm", "3cm");

		Button btnReceive = new Button("Receive");
		btnReceive.setStyleName("button");
		receivePanel.add(btnReceive);

		Label lblReceive = new Label("The query will be presented here");
		receivePanel.add(lblReceive);

		btnReceive.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				
				
				/*
				 * TODO: Make function create a Database query that returns a string. 
				 *      lblReceive.setText(String text);
				 */
				
			}
		});
		
		VerticalPanel sendPanel = new VerticalPanel();
		sendPanel
				.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		tabPanel.add(sendPanel, "Send ASCII", false);
		sendPanel.setSize("5cm", "3cm");
		
		TextArea textArea = new TextArea();
		textArea.setText("Text to be sent");
		sendPanel.add(textArea);

		Button btnSend = new Button("Send");
		btnSend.setStyleName("button");
		sendPanel.add(btnSend);
		btnSend.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				
				/*
				 * TODO: Take text in textArea send to xbn.
				 *    String sendString =  textArea.getText();
				 *    
				 */
				
			}
		});
		

		Label lblMaster = new Label("A Master Thesis Project by Pétur and David");
		verticalPanel.add(lblMaster);

	}
}
