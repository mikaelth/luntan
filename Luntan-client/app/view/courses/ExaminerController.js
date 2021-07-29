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
        r.set('decided',false);
        r.set('decisionId',null);
		
		
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
   onSendBrd: function () 
   {
/* 
		vm = this.getViewModel(),
		dest = vm.get('edBrd'),

		if(vm.get('itemstorage').getById(dest) !== null) {
			sm.getSelected().each(function(item) {
					item.set('storageId',dest);
			});

			sm.getStore().getSource().sync();
			this.getViewModel().set('mvDestStorage',0);
	        this.lookupReference('btnMove').disable();

		}
 */
   },

	onStoreContentUpdated: function (theStore, theRecords) {
		console.log("Updated in controller")
		this.up().getViewModel().data.current.cCourse.set('noExaminer',this.store.getCount());
		this.up().getViewModel().data.current.cCourse.set('note',this.store.getCount());
	},

	onStoreContentRemoved: function (theStore, theRecords) {
		console.log("Removed in controller")
		this.up().getViewModel().data.current.cCourse.set('noExaminer',this.store.getCount()-1);
		this.up().getViewModel().data.current.cCourse.set('note',this.store.getCount()-1);
	},
	
   	onBeforeRender: function (grid) {
   	},
   	
   	init: function(view) {
			
		console.log("Examiner controller init");
//Ext.util.Observable.capture(view, function(evname) {console.log(evname, arguments);})
    	

//		view.down('toolbar').insert(3,'Skapa nytt underlag för ');
/*		view.down('toolbar').insert(3,
					{

						xtype: 'combobox',
						reference: 'comboCurrentYear',
						bind: {value:'{edBrd}', store: '{availBoards}'},
						width: 550,
						typeAhead: true,
						triggerAction: 'all',
						queryMode: 'local',
						lastQuery: '',
						fieldLabel: 'Skapa nytt underlag för ',
						labelWidth: 150,
						displayField: 'displayname',
						valueField: 'id',
						listeners: {
							select: function() {
//								this.lookupReferenceHolder().lookupReference('btnCreate').enable();
							}
						}
					});
		view.down('toolbar').insert(4,
					{
							text: 'Skicka',
							reference: 'btnSendBrd',
							listeners: {
								click: 'onSendBrd'
							}
					});

 
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
 
		view.down('toolbar').insert(5,'->');
*/
   	
   	}

    
});
