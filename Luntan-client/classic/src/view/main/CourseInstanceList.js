Ext.define('Luntan.view.main.CourseInstanceList', {
    extend: 'Luntan.view.main.BasicYearListGrid',
    requires: [
    ],
    xtype: 'cilist',
	reference: 'ciList',

    title: 'Kurstillfällen',

	controller: 'courseinstancelist',
	viewModel: {type:'coursemodel'},


	bind: {store: '{cistore}'},
//	store: 'CIStore',
	
	features: [{ ftype: 'grouping',startCollapsed: false }],

    columns: [
		{ xtype: 'checkcolumn', text: 'Ny', dataIndex: 'firstInstance', editor: 'checkboxfield', editable: true, filter: 'boolean', align: 'center', width: 50 },
		{ xtype: 'checkcolumn', text: 'Balansera', dataIndex: 'balanceRequest', editor: 'checkboxfield', editable: true, filter: 'boolean', align: 'left', width: 50 },
		{ text: 'Kursgrupp', dataIndex: 'courseGroup', align: 'left', width: 150 },
		{ text: 'Kurs', dataIndex: 'courseId', align: 'left', flex: 2,
         	renderer: function(value) {
				if (Ext.getStore('CourseStore').getById(value) != undefined) {
					return Ext.getStore('CourseStore').getById(value).get('designation');
				} else {
					return value;
				}
        	},
			editor: {
				xtype: 'combobox',
				typeAhead: true,
				triggerAction: 'all',
				bind: {store: '{courses}'},
				queryMode: 'local',
				lastQuery: '',
				displayField: 'designation',
			    valueField: 'id',

			    listeners: {
					// delete the previous query in the beforequery event or set
					// combo.lastQuery = null (this will reload the store the next time it expands)
					beforequery: function(qe){
						delete qe.combo.lastQuery;
					}							    
			    }				
			}
		},
		{ text: 'Extra benämning', dataIndex: 'extraDesignation', filter: 'string', align: 'left', flex: 1, 
         	renderer: function(value) {
				if (Ext.getStore('CIDesignationStore').getById(value) != undefined) {
					return Ext.getStore('CIDesignationStore').getById(value).get('displayname');
				} else {
					return value;
				}
        	},
			editor: {
				xtype: 'combobox',
				typeAhead: true,
				triggerAction: 'all',
				bind: {store: '{extradesstore}'},
				queryMode: 'local',
				lastQuery: '',
				displayField: 'displayname',
			    valueField: 'id',
/* 
			    listeners: {
					// delete the previous query in the beforequery event or set
					// combo.lastQuery = null (this will reload the store the next time it expands)
					beforequery: function(qe){
						delete qe.combo.lastQuery;
					}							    
			    }				
 */
			}
		},
		{ text: 'Föregående kurs', dataIndex: 'preceedingCIId', align: 'left', flex: 1,
         	renderer: function(value) {
				if (Ext.getStore('CourseInstanceStore').getById(value) != undefined) {
					return Ext.getStore('CourseInstanceStore').getById(value).get('ciDesignation');
				} else {
					return value;
				}
        	},
			editor: {
				xtype: 'combobox',
				typeAhead: true,
				triggerAction: 'all',
				bind: {store: '{precedingistore}'},
				queryMode: 'local',
				lastQuery: '',
				displayField: 'ciDesignation',
			    valueField: 'id',
			    listeners: {
					// delete the previous query in the beforequery event or set
					// combo.lastQuery = null (this will reload the store the next time it expands)
					beforequery: function(qe){
						delete qe.combo.lastQuery;
					}							    
			    }				
			}
		},
		{ text: 'Registrerade studenter', dataIndex: 'registeredStudents', editor: 'textfield', filter: 'number', align: 'left', flex: 1 },
		{ text: 'Skattat studentantal', dataIndex: 'startRegStudents', editor: 'textfield', filter: 'number', align: 'left', flex: 1 },
		{ text: 'Studentantal i modell', dataIndex: 'modelStudentNumber', filter: 'number', align: 'left', flex: 1 },
		{ text: 'Betalningsmodell', dataIndex: 'fundingModelId', align: 'left', width: 200,
         	renderer: function(value) {
				if (Ext.getStore('FundingModelStore').getById(value) != undefined) {
					return Ext.getStore('FundingModelStore').getById(value).get('designation');
				} else {
					return value;
				}
        	},
			editor: {
				xtype: 'combobox',
				typeAhead: true,
				triggerAction: 'all',
				bind: {store: '{fmstore}'},
				queryMode: 'local',
				lastQuery: '',
				displayField: 'designation',
			    valueField: 'id',
 
			    listeners: {
					// delete the previous query in the beforequery event or set
					// combo.lastQuery = null (this will reload the store the next time it expands)
					beforequery: function(qe){
						delete qe.combo.lastQuery;
					}							    
			    }				
 
			}
		},
		{ text: 'Examinatorer', dataIndex: 'examiners', align: 'left', filter: 'list', flex: 1,
			renderer: function(value, p, r) { 
				if (value.length > 0) { 
						var list = []; 
						for (i = 0; i < value.length; i++) { 
							list.push(' ' + Ext.getStore('TeacherStore').getById(value[i]).get('name')) 
						} 
						return list; 
					} 
			},
			editor: new Ext.form.field.Tag({
				typeAhead: true,
				triggerAction: 'all',
				bind: {store: '{teachers}'},
				queryMode: 'local',
				lastQuery: '',
				displayField: 'name',
			    valueField: 'employeeNumber'
				
			})},
		{ text: 'Kommentarer', dataIndex: 'note', editor: 'textfield', filter: 'string', align: 'left', flex: 3 }

	],

	config : {
	}


});

