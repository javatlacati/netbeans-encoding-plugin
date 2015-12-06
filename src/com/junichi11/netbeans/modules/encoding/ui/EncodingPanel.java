/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2013 Oracle and/or its affiliates. All rights reserved.
 *
 * Oracle and Java are registered trademarks of Oracle and/or its affiliates.
 * Other names may be trademarks of their respective owners.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common
 * Development and Distribution License("CDDL") (collectively, the
 * "License"). You may not use this file except in compliance with the
 * License. You can obtain a copy of the License at
 * http://www.netbeans.org/cddl-gplv2.html
 * or nbbuild/licenses/CDDL-GPL-2-CP. See the License for the
 * specific language governing permissions and limitations under the
 * License.  When distributing the software, include this License Header
 * Notice in each file and include the License file at
 * nbbuild/licenses/CDDL-GPL-2-CP.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the GPL Version 2 section of the License file that
 * accompanied this code. If applicable, add the following below the
 * License Header, with the fields enclosed by brackets [] replaced by
 * your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 *
 * If you wish your version of this file to be governed by only the CDDL
 * or only the GPL Version 2, indicate your decision by adding
 * "[Contributor] elects to include this software in this distribution
 * under the [CDDL or GPL Version 2] license." If you do not indicate a
 * single choice of license, a recipient has the option to distribute
 * your version of this file under either the CDDL, the GPL Version 2 or
 * to extend the choice of license to its licensees as provided above.
 * However, if you add GPL Version 2 code and therefore, elected the GPL
 * Version 2 license, then the option applies only if the new code is
 * made subject to such option by the copyright holder.
 *
 * Contributor(s):
 *
 * Portions Copyrighted 2013 Sun Microsystems, Inc.
 */
package com.junichi11.netbeans.modules.encoding.ui;

import com.junichi11.netbeans.modules.encoding.OpenInEncodingQueryImpl;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Collection;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;
import org.netbeans.api.editor.EditorRegistry;
import org.netbeans.api.queries.FileEncodingQuery;
import org.netbeans.modules.editor.NbEditorUtilities;
//import org.netbeans.spi.queries.FileEncodingQueryImplementation;
import org.openide.cookies.CloseCookie;
import org.openide.cookies.EditorCookie;
import org.openide.cookies.OpenCookie;
import org.openide.filesystems.FileObject;
import org.openide.loaders.DataObject;
import org.openide.util.Exceptions;
import org.openide.util.Lookup;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;
import org.openide.util.Utilities;
import org.openide.windows.Mode;
import org.openide.windows.TopComponent;
import org.openide.windows.WindowManager;

/**
 *
 * @author junichi11
 */
public class EncodingPanel extends JPanel implements LookupListener {

    /**
     * number to verify correct serialization/deserialization
     */
    private static final long serialVersionUID = 1L;

    private Lookup.Result<FileObject> result;
    private FileObject currentFileObject;
    private final OpenInEncodingQueryImpl queryImpl = new OpenInEncodingQueryImpl();

    /**
     * Creates new form EncodingPanel
     */
    public EncodingPanel() {
        initComponents();
        addLookupListener();
        init();
    }

    private void init() {
        defaultEncodingLabel.setText(""); // NOI18N
        final Collection<? extends Charset> charsets = Charset.availableCharsets().values();
        DefaultComboBoxModel defaultComboBoxModel = new DefaultComboBoxModel();
        defaultComboBoxModel.addElement(""); // NOI18N
        for (Charset charset : charsets) {
            defaultComboBoxModel.addElement(charset.name());
        }
        encodingComboBox.setModel(defaultComboBoxModel);
        encodingComboBox.addItemListener(new DefaultItemListener());
        setEncodingEnabled(false);
    }

    private void addLookupListener() {
        result = Utilities.actionsGlobalContext().lookupResult(FileObject.class);
        result.addLookupListener(this);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        encodingComboBox = new javax.swing.JComboBox();
        defaultEncodingLabel = new javax.swing.JLabel();

        encodingComboBox.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                encodingComboBoxMouseReleased(evt);
            }
        });
        encodingComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                encodingComboBoxActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(defaultEncodingLabel, org.openide.util.NbBundle.getMessage(EncodingPanel.class, "EncodingPanel.defaultEncodingLabel.text")); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(encodingComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(defaultEncodingLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(encodingComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(defaultEncodingLabel))
        );

        defaultEncodingLabel.getAccessibleContext().setAccessibleName(org.openide.util.NbBundle.getMessage(EncodingPanel.class, "EncodingPanel.defaultEncodingLabel.AccessibleContext.accessibleName")); // NOI18N
    }// </editor-fold>//GEN-END:initComponents

    private void encodingComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_encodingComboBoxActionPerformed

    }//GEN-LAST:event_encodingComboBoxActionPerformed

    private void encodingComboBoxMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_encodingComboBoxMouseReleased
        setEncodingEnabled(isEditor());
    }//GEN-LAST:event_encodingComboBoxMouseReleased

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel defaultEncodingLabel;
    private javax.swing.JComboBox encodingComboBox;
    // End of variables declaration//GEN-END:variables

    @Override
    public void resultChanged(LookupEvent lookupEvent) {
        Lookup.Result r = (Lookup.Result) lookupEvent.getSource();
        Collection c = r.allInstances();
        String en = ""; // NOI18N
        if (!c.isEmpty()) {
            currentFileObject = (FileObject) c.iterator().next();
            Charset encoding = getEncoding(currentFileObject);
            if (encoding != null) {
                en = encoding.name();
            }
        } else {
            currentFileObject = null;
        }
        final String encodingName = en;

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // XXX show project encoding?
//                setDefaultEncoding(currentFileObject);
                setEncodingEnabled(currentFileObject);
                encodingComboBox.setSelectedItem(encodingName); // NOI18N
            }
        });
    }

    private boolean isEditor() {
        TopComponent activated = TopComponent.getRegistry().getActivated();
        if (activated != null) {
            WindowManager windowManager = WindowManager.getDefault();
            Mode mode = windowManager.findMode(activated);
            if (mode != null) {
                String name = mode.getName();
                return name.equals("editor"); // NOI18N
            }
        }
        return false;
    }

    private Charset getEncoding(FileObject fileObject) {
        Charset encoding = queryImpl.getEncoding(fileObject);
        if (encoding == null) {
            encoding = FileEncodingQuery.getEncoding(fileObject);
        }
        return encoding;
    }

    //TODO unused method
//    private void setDefaultEncoding(FileObject fileObject) {
//        Charset charset = null;
//        if (fileObject != null) {
//            for (FileEncodingQueryImplementation impl : Lookup.getDefault().lookupAll(FileEncodingQueryImplementation.class)) {
//                if (impl instanceof OpenInEncodingQueryImpl) {
//                    continue;
//                }
//                Charset encoding = impl.getEncoding(fileObject);
//                if (encoding != null) {
//                    charset = encoding;
//                    break;
//                }
//            }
//        }
//
//        String encoding = ""; // NOI18N
//        if (charset != null) {
//            encoding = String.format("(%s)", charset.name()); // NOI18N
//        }
//        defaultEncodingLabel.setText(encoding);
//    }
    private void setEncodingEnabled(boolean isEnabled) {
        encodingComboBox.setEnabled(isEnabled);
    }

    private void setEncodingEnabled(FileObject fileObject) {
        if (fileObject == null || fileObject.isFolder()) {
            setEncodingEnabled(false);
            return;
        }

        FileObject lastFocusedFileObject = getLastFocusedFileObject();
        if (fileObject == lastFocusedFileObject) {
            setEncodingEnabled(true);
            return;
        }

        setEncodingEnabled(isEditor());
    }

    private FileObject getLastFocusedFileObject() {
        JTextComponent editor = EditorRegistry.lastFocusedComponent();
        if (editor == null) {
            return null;
        }

        Document document = editor.getDocument();
        if (document == null) {
            return null;
        }

        return NbEditorUtilities.getFileObject(document);
    }

    //~ Inner class
    private class DefaultItemListener implements ItemListener {

        //public DefaultItemListener() {}
        @Override
        public void itemStateChanged(ItemEvent e) {
            if (e.getStateChange() != ItemEvent.SELECTED) {
                return;
            }

            FileObject fileObject = getLastFocusedFileObject();
            if (fileObject == null) {
                return;
            }

            // same encoding?
            Charset encoding = getEncoding(fileObject);
            String currentEncoding = encoding.name();
            String selectedEncoding = (String) e.getItem();
            // #1 encoding is empty when snippet is inserted with palette
            if (selectedEncoding.equals(currentEncoding) || selectedEncoding.isEmpty()) {
                return;
            }

            // set encoding to attribute, reopen file
            if (currentFileObject != null && fileObject == currentFileObject) {
                try {
                    currentFileObject.setAttribute(OpenInEncodingQueryImpl.ENCODING, selectedEncoding);
                    final DataObject dobj = DataObject.find(currentFileObject);

                    reopen(dobj);

                } catch (IOException ex) {
                    Exceptions.printStackTrace(ex);
                } catch (InterruptedException ex) {
                    Exceptions.printStackTrace(ex);
                }
            }
        }

        private void reopen(DataObject dobj) throws InterruptedException {
            close(dobj);

            // XXX java.lang.AssertionError is occurred
            Thread.sleep(200);

            open(dobj);
        }

        private void close(DataObject dobj) {
            CloseCookie cc = dobj.getLookup().lookup(CloseCookie.class);
            EditorCookie ec = dobj.getLookup().lookup(EditorCookie.class);

            if (cc != null) {
                cc.close();
            }
            if (ec != null) {
                ec.close();
            }
        }

        private void open(DataObject dobj) {
            OpenCookie oc = dobj.getLookup().lookup(OpenCookie.class);
            if (oc != null) {
                oc.open();
            }
        }

    }
}
