Ext.define('Luntan.view.courses.CourseModel', {
    extend: 'Ext.app.ViewModel',

    alias: 'viewmodel.coursemodel',

    data: {
		current : {
			edocId : '',
			ci : null,
			cid : null
		}
    },
    
    stores: {
		coursegroups: 'CourseGroupStore',    	
		courses: 'CourseStore',    	
  
		extradesstore: {
			type: 'chained',
			source: 'CIDesignationStore'
		},    	
 		usedYears: {
 			type:'chained',
 			source:'EconomyDocStore',
 			sorters: [{property:'year', direction: 'DESC'}]
 		},
		cistore: {
			type: 'chained',
			source: 'CourseInstanceStore',
			filters: [{property: 'economyDocId', value: '{current.edocId}', exactMatch: true}],
			sorters: [{property:'courseName', direction: 'ASC'}],
			groupField: 'courseGroup'
		},    	
		citaskstore: {
			type: 'chained',
			source: 'CourseInstanceStore',
			filters: [{property: 'economyDocId', value: '{current.edocId}', exactMatch: true}],
			sorters: [{property:'courseName', direction: 'ASC'}],
			groupField: 'courseGroup'
		},
		precedingistore: {
			type: 'chained',
			source: 'CourseInstanceStore',
			filters: [{property: 'courseId', value: '{current.cid}', exactMatch: true}],
			sorters: [{property:'ciDesignation', direction: 'ASC'}]
		},
		fmstore : {
			type: 'chained',
			source: 'FundingModelStore'
		}
    	
/*
		courses: {
			type: 'chained',
			source: 'CourseStore'
		}
 */
		    	
	},
	
	formulas: {
        workingEDoc: {
            // We need to bind deep to be notified on each model change
            bind: {
                bindTo: '{comboCurrentYear.selection.id}', //--> reference configurated on the grid view (reference: comboCurrentYear)
                deep: true
            },
            get: function(edocId) {
            	this.set('current.edocId', edocId);
                return edocId;
            }
        },
 
        currentCI: {
            // We need to bind deep to be notified on each model change
            bind: {
                bindTo: '{ciList.selection}', //--> reference configurated on the grid view (reference: ciList)
                deep: true
            },
            get: function(ci) {
            	this.set('current.ci', ci);
                return ci;
            }
        },

        currentCourse: {
            // We need to bind deep to be notified on each model change
            bind: {
                bindTo: '{ciList.selection.courseId}', //--> reference configurated on the grid view (reference: ciList)
                deep: true
            },
            get: function(cid) {
            	this.set('current.cid', cid);
                return cid;
            }
 
		}
	}

});
