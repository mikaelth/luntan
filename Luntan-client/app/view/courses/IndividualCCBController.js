Ext.define('Luntan.view.courses.IndividualCCBController', {
    extend: 'Luntan.view.BasicController',

    alias: 'controller.credbaselist',

    
    onCreate: function()
    {
        var grid = this.getView(),
        	theStore = grid.getStore(),
        	thisCCB = this.getViewModel().get('current.cbase.id');
         grid.plugins[0].cancelEdit();

        // Create a model instance
        var r = Ext.create('Luntan.model.IndCourseCreditBasis');
//        r.set('economyDocId',thisEDoc);

		var rec = grid.getStore().insert(0, r);
        grid.plugins[0].startEdit(rec[0]);
        
    },

   	onBeforeRender: function (grid) {
   	},

   	onUpdateInactive: function () {
   	},

	onStoreContentUpdated: function (theStore, theRecords) {
		console.log("Updated in controller")
//		this.up().down('credbasereglist').store.getSource().reload();

		var otherStore = this.up().down('credbasereglist').store.hasOwnProperty('source') ? 
			this.up().down('credbasereglist').store.getSource() : 
			this.up().down('credbasereglist').store;
			
		otherStore.reload();

	},

	onStoreContentRemoved: function (theStore, theRecords) {
		console.log("Removed in controller")
		var otherStore = this.up().down('credbasereglist').store.hasOwnProperty('source') ? 
			this.up().down('credbasereglist').store.getSource() : 
			this.up().down('credbasereglist').store;
			
		otherStore.reload();
	},

   	init: function (view) {

		var tb = view.down('toolbar');
/* 						
		tb.insert(2,
			{
				xtype: 'button',
				showText: true,
				tooltip:'Slå upp kurser i studieplaner',
				text: 'Kurser per program',
				reference: 'btnProgramCourses',
				disabled: false,
				listeners: {
				  click: 'onProgramCourses'
				}
			});			


		
		tb.insert(1,
			{
				xtype: 'button',
				showText: true,
				tooltip:'Kontrollerar om det i kursplanen anges att Kursen är avvecklad',
				text: 'Uppdatera inaktiva',
				reference: 'btnInactiveUpdate',
				disabled: false,
				listeners: {
				  click: 'onUpdateInactive'
				}
			});			
        
 */

		console.log("ICCB controller init");

	}

});
