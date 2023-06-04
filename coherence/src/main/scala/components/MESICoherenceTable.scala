package components

import coherences.PMESI

class MESICoherenceTable extends CoherenceTableGenerator(
  new CacheEvent { },
  new PMESI {},
  new RequestType { }
) {

  override def table: Map[Event, Do]  = Map(
    // From I
    Event(CE.LOAD,CohS.I) -> Do(CohS.IS_AD,"ADD_TO_REQ_QUEUE").markValid.broadcast(R.GETS),
    Event(CE.STORE,CohS.I) -> Do(CohS.IM_AD,"ADD_TO_REQ_QUEUE").markValid.broadcast(R.GETM),
    Event(CE.OWN_GETM,CohS.IM_AD) -> Do(CohS.IM_D, "UPDATE_CACHE"),
    //Event(CacheEvent.OTHER_GETM,CohS.IM_AD, Hit) -> Do(CohS.IM_AD, "IGNORE"),
    //Event(CacheEvent.OTHER_GETS,CohS.IM_AD, Hit) -> Do(CohS.IM_AD, "IGNORE"),
    //Event(CacheEvent.OTHER_PUTM,CohS.IM_AD, Hit) -> Do(CohS.IM_AD, "IGNORE"),
    Event(CE.OTHER_GETM,CohS.IM_D) -> Do(CohS.IM_DUI, "UPDATE_CACHE"),
    Event(CE.OTHER_GETS,CohS.IM_D) -> Do(CohS.IM_DUS, "UPDATE_CACHE"),
    //Event(CE.OTHER_PUTM,CohS.IM_D, Hit) -> Do(CohS.IM_D, "IGNORE"),
    //Event(CE.OTHER_GETM,CohS.IM_DUI, Hit) -> Do(CohS.IM_DUI, "IGNORE"),
    //Event(CE.OTHER_GETS,CohS.IM_DUI, Hit) -> Do(CohS.IM_DUI, "IGNORE"),
    //Event(CE.OTHER_PUTM,CohS.IM_DUI, Hit) -> Do(CohS.IM_DUI, "IGNORE"),
    //Event(CE.OTHER_GETS,CohS.IM_DUS, Hit) -> Do(CohS.IM_DUS, "IGNORE"),
    Event(CE.OTHER_GETM,CohS.IM_DUS) -> Do(CohS.IM_DUI, "UPDATE_CACHE"),
    //Event(CE.OTHER_PUTM,CohS.IM_DUS, Hit) -> Do(CohS.IM_DUS, "IGNORE"),
    Event(CE.EDATA, CohS.IM_D) -> Do(CohS.M, "REFILL_CACHE").markDirty, // This should be changed
    Event(CE.DATA, CohS.IM_D) -> Do(CohS.M, "REFILL_CACHE").markDirty,
    //Event(CE.OTHER_PUTM,CohS.IM_D, Hit) -> Do(CohS.IM_D, "IGNORE"),
    Event(CE.DATA, CohS.IM_DUI) -> Do(CohS.IM_UI, "REFILL_CACHE"),
    Event(CE.DATA, CohS.IM_DUS) -> Do(CohS.IM_US, "REFILL_CACHE"),
    Event(CE.EDATA, CohS.IM_DUI) -> Do(CohS.IM_UI, "REFILL_CACHE"),
    Event(CE.EDATA, CohS.IM_DUS) -> Do(CohS.IM_US, "REFILL_CACHE"),

    Event(CE.STORE, CohS.IM_UI) -> Do(CohS.MI_A, "WRITE_DATA_ARRAY_ADD_TO_CACHE_RESP_Q").markDirty.broadcast(R.PUTM),
    Event(CE.STORE, CohS.IM_US) -> Do(CohS.MS_A, "WRITE_DATA_ARRAY_ADD_TO_CACHE_RESP_Q").markDirty.broadcast(R.PUTM),
    //Event(CE.OTHER_GETM, CohS.IS_AD, Hit) -> Do(CohS.IS_AD, "IGNORE"),
    //Event(CE.OTHER_GETS, CohS.IS_AD, Hit) -> Do(CohS.IS_AD, "IGNORE"),
    //Event(CE.OTHER_PUTM, CohS.IS_AD, Hit) -> Do(CohS.IS_AD, "IGNORE"),
    Event(CE.OWN_GETS, CohS.IS_AD) -> Do(CohS.IS_D, "UPDATE_CACHE"),
    //Event(CE.OTHER_GETM, CohS.IS_D, Hit) -> Do(CohS.IS_DUI, "UPDATE_CACHE"),
    Event(CE.OTHER_GETM, CohS.IS_D) -> Do(CohS.IS_DI, "UPDATE_CACHE"),
    Event(CE.OTHER_GETS, CohS.IS_D) -> Do(CohS.IS_D, "UPDATE_CACHE"),
    //Event(CE.OTHER_PUTM, CohS.IS_D, Hit) -> Do(CohS.IS_D, "IGNORE"),
    Event(CE.DATA, CohS.IS_D) -> Do(CohS.S, "REFILL_CACHE"),
    Event(CE.EDATA, CohS.IS_D) -> Do(CohS.E, "REFILL_CACHE"),
    //Event(CE.DATA, CohS.IS_DUI, Hit) -> Do(CohS.IS_UI, "REFILL_CACHE"),
    //Event(CE.EDATA, CohS.IS_DUI, Hit) -> Do(CohS.IS_UI, "REFILL_CACHE"),
    Event(CE.DATA, CohS.IS_DI) -> Do(CohS.IS_UI, "REFILL_CACHE"),
    Event(CE.EDATA, CohS.IS_DI) -> Do(CohS.IS_UI, "REFILL_CACHE"),
    Event(CE.LOAD, CohS.IS_UI) -> Do(CohS.I, "ADD_TO_CACHE_RESP_QUEUE"),
    // From S
    Event(CE.LOAD, CohS.S) -> Do(CohS.S, "ADD_TO_CACHE_RESP_QUEUE"),
    Event(CE.REPLACEMENT, CohS.S) -> Do(CohS.SI_A, "ADD_TO_WB_QUEUE").broadcast(R.PUTS),
    Event(CE.OWN_PUTS, CohS.SI_A) -> Do(CohS.I, "REMOVE_FROM_CACHE").reset,
    Event(CE.OTHER_GETM, CohS.S) -> Do(CohS.I, "UPDATE_CACHE").reset,
    Event(CE.OTHER_UPG, CohS.S) -> Do(CohS.I, "UPDATE_CACHE").reset,
    //Event(CE.OTHER_GETS, CohS.S) -> Do(CohS.S, "IGNORE"),
    //Event(CE.OTHER_PUTM, CohS.S) -> Do(CohS.S, "IGNORE"),
    Event(CE.STORE, CohS.S) -> Do(CohS.SM_W, "ADD_TO_REQ_QUEUE").broadcast(R.UPG),
    //Event(CE.REPLACEMENT, CohS.S) -> Do(CohS.IM_AD, "ADD_TO_REQ_QUEUE").broadcast(R.GETM),
    //Event(CE.OTHER_GETS, CohS.SM_W) -> Do(CohS.SM_W, "IGNORE"),
    Event(CE.OTHER_GETM, CohS.SM_W) -> Do(CohS.IM_W, "UPDATE_CACHE"),
    Event(CE.OTHER_UPG, CohS.SM_W) -> Do(CohS.IM_W, "UPDATE_CACHE"),
    //Event(CE.OTHER_PUTM, CohS.SM_W) -> Do(CohS.SM_W, "IGNORE"),
    Event(CE.OWN_UPG,    CohS.SM_W) -> Do(CohS.M,     "ADD_TO_CACHE_RESP_QUEUE_STORE").dirtify,
    //Event(CE.OTHER_GETS, CohS.IM_W) -> Do(CohS.IM_W, "IGNORE"),
    //Event(CE.OTHER_GETM, CohS.IM_W) -> Do(CohS.IM_W, "IGNORE"),
    Event(CE.OWN_UPG,    CohS.IM_W) -> Do(CohS.IM_AD, "ADD_TO_REQ_QUEUE").broadcast(R.GETM),
    // From E
    Event(CE.LOAD, CohS.E) -> Do(CohS.E, "ADD_TO_CACHE_RESP_QUEUE"),
    Event(CE.REPLACEMENT, CohS.E) -> Do(CohS.EI_A, "ADD_TO_WB_QUEUE").broadcast(R.PUTM),
    Event(CE.STORE, CohS.E) -> Do(CohS.M, "ADD_TO_CACHE_RESP_QUEUE_STORE").markDirty,
    // Event(CE.REPLACEMENT, CohS.E) -> Do(CohS.EI_A, "ADD_TO_WB_QUEUE").broadcast(R.PUTM),
    Event(CE.OTHER_GETS, CohS.E) -> Do(CohS.ES_A, "ADD_TO_WB_QUEUE").broadcast(R.PUTM),
    Event(CE.OTHER_GETM, CohS.E) -> Do(CohS.EI_A, "ADD_TO_WB_QUEUE").broadcast(R.PUTM),
    //Event(CE.OTHER_PUTM, CohS.E) -> Do(CohS.E, "IGNORE"),
    Event(CE.LOAD, CohS.EI_A) -> Do(CohS.EI_A, "ADD_TO_CACHE_RESP_QUEUE"),
    //*Event(CE.STORE, CohS.EI_A) -> Do(CohS.MI_A, "ADD_TO_CACHE_RESP_QUEUE_STORE").markDirty,
    //Event(CE.OTHER_GETS, CohS.EI_A) -> Do(CohS.EI_A, "IGNORE"),
    //Event(CE.OTHER_GETM, CohS.EI_A) -> Do(CohS.EI_A, "IGNORE"),
    //Event(CE.OTHER_PUTM, CohS.EI_A) -> Do(CohS.EI_A, "IGNORE"),
    Event(CE.OWN_PUTM, CohS.EI_A) -> Do(CohS.I, "REMOVE_FROM_CACHE").reset, // replay
    // Otherwise, the line could still be in some other states.
    Event(CE.LOAD, CohS.ES_A) -> Do(CohS.ES_A, "ADD_TO_CACHE_RESP_QUEUE"),
    //*Event(CE.STORE, CohS.ES_A) -> Do(CohS.MI_A, "ADD_TO_CACHE_RESP_QUEUE_STORE").markDirty,
    //Event(CE.OTHER_GETS, CohS.ES_A) -> Do(CohS.ES_A, "IGNORE"),
    Event(CE.OTHER_GETM, CohS.ES_A) -> Do(CohS.EI_A, "UPDATE_CACHE"), // no need to response
    //Event(CE.OTHER_PUTM, CohS.ES_A) -> Do(CohS.ES_A, "IGNORE"),
    Event(CE.OWN_PUTM, CohS.ES_A) -> Do(CohS.S, "UPDATE_CACHE"),
    // From M
    Event(CE.LOAD, CohS.M) -> Do(CohS.M, "ADD_TO_CACHE_RESP_QUEUE"),
    Event(CE.STORE, CohS.M) -> Do(CohS.M, "ADD_TO_CACHE_RESP_QUEUE_STORE"),
    Event(CE.REPLACEMENT, CohS.M) -> Do(CohS.MI_WB, "ADD_TO_WB_QUEUE").broadcast(R.PUTM),
    Event(CE.REPLACEMENT, CohS.M) -> Do(CohS.MI_WB, "ADD_TO_WB_QUEUE").broadcast(R.PUTM),
    Event(CE.OTHER_GETS, CohS.M) -> Do(CohS.MS_WB, "ADD_TO_WB_QUEUE").broadcast(R.PUTM),
    Event(CE.OTHER_GETM, CohS.M) -> Do(CohS.MI_WB, "ADD_TO_WB_QUEUE").broadcast(R.PUTM),

    Event(CE.REPLACEMENT, CohS.MS_WB) -> Do(CohS.MI_WB, "ADD_TO_WB_QUEUE").broadcast(R.PUTS),
    //Event(CE.OTHER_PUTM, CohS.M) -> Do(CohS.M, "IGNORE"),
    Event(CE.LOAD, CohS.MS_WB) -> Do(CohS.MS_WB, "ADD_TO_CACHE_RESP_QUEUE"),
    //*Event(CE.STORE, CohS.MS_WB) -> Do(CohS.MS_WB, "ADD_TO_CACHE_RESP_QUEUE_STORE"),
    //Event(CE.OTHER_GETS, CohS.MS_WB, Hit) -> Do(CohS.MS_WB, "IGNORE"),
    //Event(CE.OTHER_GETM, CohS.MS_WB, Hit) -> Do(CohS.MS_WB, "IGNORE"),
    Event(CE.OWN_PUTM, CohS.MS_WB) -> Do(CohS.S, "UPDATE_CACHE").clean,
    Event(CE.OWN_PUTM, CohS.MS_A) -> Do(CohS.S, "UPDATE_CACHE").clean,
    Event(CE.OTHER_GETM, CohS.MS_WB) -> Do(CohS.MI_WB, "UPDATE_CACHE"),
    Event(CE.OTHER_GETM, CohS.MS_A) -> Do(CohS.MI_A, "UPDATE_CACHE"),
    //Event(CE.OTHER_GETS, CohS.MI_WB, Hit) -> Do(CohS.MI_WB, "IGNORE"),
    //Event(CE.OTHER_GETM, CohS.MI_WB, Hit) -> Do(CohS.MI_WB, "IGNORE"),
    Event(CE.OWN_PUTM, CohS.MI_WB) -> Do(CohS.I, "REMOVE_FROM_CACHE").reset,
    Event(CE.OWN_PUTM, CohS.MI_A) -> Do(CohS.I, "REMOVE_FROM_CACHE").reset,
    // Impossible transitions
    Event(CE.OTHER_PUTM, CohS.MI_WB) -> Do(CohS.I, "IMPOSSIBLE"),
    Event(CE.OTHER_PUTM, CohS.MS_WB) -> Do(CohS.I, "IMPOSSIBLE"),
    Event(CE.OTHER_PUTM, CohS.M) -> Do(CohS.I, "IMPOSSIBLE"),
    Event(CE.OTHER_PUTM, CohS.MI_A) -> Do(CohS.I, "IMPOSSIBLE"),
    Event(CE.OTHER_PUTM, CohS.E) -> Do(CohS.I, "IMPOSSIBLE"),
    Event(CE.OTHER_PUTM, CohS.EI_A) -> Do(CohS.I, "IMPOSSIBLE"),
    Event(CE.OTHER_PUTM, CohS.ES_A) -> Do(CohS.I, "IMPOSSIBLE"),
    //Event(CE.OTHER_UPG,  CohS.M, Hit) -> Do(CohS.I, "IMPOSSIBLE"),
    //Event(CE.OTHER_UPG,  CohS.MI_WB, Hit) -> Do(CohS.I, "IMPOSSIBLE"),
    //Event(CE.OTHER_UPG,  CohS.MS_WB, Hit) -> Do(CohS.I, "IMPOSSIBLE"),
    //Event(CE.OTHER_UPG,  CohS.M, Hit) -> Do(CohS.I, "IMPOSSIBLE"),
    //Event(CE.OTHER_UPG,  CohS.MI_A, Hit) -> Do(CohS.I, "IMPOSSIBLE"),
    //Event(CE.OTHER_UPG,  CohS.E, Hit) -> Do(CohS.I, "IMPOSSIBLE"),
    //Event(CE.OTHER_UPG,  CohS.EI_A, Hit) -> Do(CohS.I, "IMPOSSIBLE"),
    //Event(CE.OTHER_UPG,  CohS.ES_A, Hit) -> Do(CohS.I, "IMPOSSIBLE"),
  )
  override def getCoherenceWBStates: List[Int] = List(CohS.MS_A, CohS.MI_A, CohS.MI_WB,
    CohS.MS_WB, CohS.EI_A, CohS.ES_A)
}

