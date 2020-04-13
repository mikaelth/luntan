Ext.define('Luntan.view.courses.ExaminerController', {
    extend: 'Luntan.view.BasicController',

    alias: 'controller.examinerlist',

    
    onCreate: function()
    {
        var grid = this.getView(), 
        	thisCourseId = this.getViewModel().get('current.cid'),
        	theStore = grid.getStore(),
			itemNum = theStore.data.items.length;
			
		grid.plugins[0].cancelEdit();

        // Create a model instance
        var r = Ext.create('Luntan.model.Examiner');
		r.set('rank', theStore.nextRank());
        r.set('courseId',thisCourseId);
		
		
		var rec = theStore.insert(itemNum, r);
//		grid.ownerGrid.refreshRank();
        grid.plugins[0].startEdit(rec[0]);
        
    },

   onRemove: function()
    {
        var grid = this.getView();
        var sm = grid.getSelectionModel();
            grid.plugins[0].cancelEdit();
            grid.getStore().remove(sm.getSelection());
            if (grid.getStore().getCount() > 0) {
                sm.select(0);
            }
		grid.ownerGrid.refreshRank();
   },

   	onBeforeRender: function (grid) {
   	},
   	
   	init: function(view) {

		view.down('toolbar').insert(3,'Skapa nytt underlag för ');
		view.down('toolbar').insert(4,
					{	xtype: 'button',
						text: 'NUN',
						reference: 'btnCreateNUN',
						disabled: false,
						 listeners: {
							click: 'onShowOverview'
						}
					});
		view.down('toolbar').insert(5,
					{	xtype: 'button',
						text: 'TUN',
						reference: 'btnCreateNUN',
						disabled: false,
						 listeners: {
							click: 'onShowOverview'
						}
					});
		view.down('toolbar').insert(6,'->');

   	
   	}

    
});
