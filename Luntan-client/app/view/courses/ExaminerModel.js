Ext.define('Luntan.view.courses.ExaminerModel', {
    extend: 'Ext.app.ViewModel',

    alias: 'viewmodel.examinermodel',

    data: {
		current : {
			cid: null,
			did: null,
			edBrd : '',
		}
    },
    
    stores: {
		coursegroups: 'CourseGroupStore',    	

		courses: 'CourseStore',    	

  		availBoards: {
 			type:'chained',
 			source:'EduBoardStore',
 			sorters: [{property:'displayname', direction: 'DESC'}]
 		},
 
  		examinerslistings: {
 			type:'chained',
 			source:'ExaminersListStore',
 			sorters: [
 				{property:'decided', direction: 'ASC'},
 				{property:'decisionDate', direction: 'DESC'}
 			],
			listeners: {
				update: function ( store, record, operation, modifiedFieldNames, details, eOpts) {
					if (operation == 'commit' && modifiedFieldNames.includes('id') ) {
						// This means a new record is created at the server
						Ext.getStore('ExaminerStore').reload();
					}
				}
			}
 
  		},

 
/* 
		listedteachers : {
			type: 'chained',
			source: 'TeacherStore'
		},
 */
		teachers : {
			type: 'chained',
			source: 'TeacherStore',
			sorters: [{property:'name', direction: 'ASC'}],
		},

		examiners : {
			type: 'chained',
			source: 'ExaminerStore',
			filters: [
 				{property: 'courseId', value: '{current.cid}', exactMatch: true}, 
 				{property: 'decided', value: false, exactMatch: true}
			],
//			sorters: {property:'rank', direction: 'ASC'},
			nextRank : function() { 
				var rs = this.data.items, v = 0; 
				for(var i = 0; i < rs.length; i++){ 
					v = Math.max(v, (1 * rs[i].data['rank']) || 0); 
				} 
				return v+1; 
			} 			
			
		},

		listedexaminers : {
			type: 'chained',
			source: 'ExaminerStore',
			filters: [
				{property: 'decisionId', value: '{current.did}', exactMatch: true} 
			],
			sorters: [{property:'courseId', direction: 'ASC'},{property:'rank', direction: 'ASC'}]
/* 
			nextRank : function() { 
				var rs = this.data.items, v = 0; 
				for(var i = 0; i < rs.length; i++){ 
					v = Math.max(v, (1 * rs[i].data['rank']) || 0); 
				} 
				return v+1; 
			} 			
 */
			
		}
		    	
	},
	
	formulas: {

        currentCourse: {
            // We need to bind deep to be notified on each model change
            bind: {
                bindTo: '{courselist.selection.id}', //--> reference configurated on the grid view (reference: courselist)
                deep: true
            },
            get: function(cid) {
            	this.set('current.cid', cid);
                return cid;
            }
 
		},
        currentDecision: {
            // We need to bind deep to be notified on each model change
            bind: {
                bindTo: '{examinerslisting.selection.id}', //--> reference configurated on the grid view (reference: examinerslisting)
                deep: true
            },
            get: function(did) {
            	this.set('current.did', did);
                return did;
            }
 
		}
 	}

});
