Ext.define('Luntan.view.main.BasicYearListGrid', {
    extend: 'Luntan.view.main.BasicListGrid',
    requires: [
        'Ext.selection.RowModel',
        'Ext.Editor',
        'Ext.grid.*',
        'Ext.data.*',
        'Ext.util.*',
        'Ext.form.*'
    ],
	xtype: 'basicyearlistgrid',


	dockedItems: [{
		xtype: 'toolbar',
		items: [
		 			{
//						text: 'Reload all',
						text: 'Hämta alla',
						reference: 'btnReload',
						disabled: false,
						 listeners: {
							click: 'onReload'
						}
					}, {
						text: 'Skriv ut',
						reference: 'btnPrint',
						disabled: false,
						handler: function() {
							Ext.ux.grid.Printer.printAutomatically = false;
							Ext.ux.grid.Printer.closeAutomaticallyAfterPrint = false;
							Ext.ux.grid.Printer.print(this.up('grid'));   
						}
					}, {

						xtype: 'combobox',
						reference: 'comboCurrentYear',
						bind: {value: '{workingEDoc}', store: '{usedYears}'},
						width: 200,
						typeAhead: true,
						triggerAction: 'all',
						queryMode: 'local',
						lastQuery: '',
						fieldLabel: 'Välj år',
						labelWidth: 50,
						displayField: 'year',
						valueField: 'id',
						listeners: {
							select: function() {
//								this.lookupReferenceHolder().lookupReference('btnCreate').enable();
							}
						}
					},
					'->', {
//						text: 'Remove',
						text: 'Tag bort post',
						reference: 'btnRemove',
						disabled: true,
						bind:{disabled: '{current.edoc.locked}'},
						 listeners: {
							click: 'onRemove'
						}
					}, {
//						text: 'Create',
						text: 'Ny post',
						reference: 'btnCreate',
						disabled: true,
						bind:{disabled: '{disableEditBtns}'},
						listeners: {
							click: 'onCreate'
						}
					}, {
//						text: 'Save',
						text: 'Spara till db',
						reference: 'btnSave',
						listeners: {
							click: 'onSave'
						}
					}

			]
	}]
	
});

