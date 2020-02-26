package org.topj.methods.response.block;

import com.alibaba.fastjson.annotation.JSONField;

public class LightUnitState{

   @JSONField(name = "m_balance_change")
   private Integer mBalanceChange;

   @JSONField(name = "m_last_error")
   private Integer mLastError;

   @JSONField(name = "m_load")
   private Integer mLoad;

   @JSONField(name = "m_native_property")
   private String mNativeProperty;

   @JSONField(name = "m_object_flags")
   private Integer mObjectFlags;

   @JSONField(name = "m_object_id")
   private Integer mObjectId;

   @JSONField(name = "m_object_type")
   private Integer mObjectType;

   @JSONField(name = "m_property_hash")
   private String mPropertyHash;

   @JSONField(name = "m_property_log_hash")
   private String mPropertyLogHash;

   public Integer getmBalanceChange() {
       return mBalanceChange;
   }

   public void setmBalanceChange(Integer mBalanceChange) {
       this.mBalanceChange = mBalanceChange;
   }

   public Integer getmLastError() {
       return mLastError;
   }

   public void setmLastError(Integer mLastError) {
       this.mLastError = mLastError;
   }

   public Integer getmLoad() {
       return mLoad;
   }

   public void setmLoad(Integer mLoad) {
       this.mLoad = mLoad;
   }

   public String getmNativeProperty() {
       return mNativeProperty;
   }

   public void setmNativeProperty(String mNativeProperty) {
       this.mNativeProperty = mNativeProperty;
   }

   public Integer getmObjectFlags() {
       return mObjectFlags;
   }

   public void setmObjectFlags(Integer mObjectFlags) {
       this.mObjectFlags = mObjectFlags;
   }

   public Integer getmObjectId() {
       return mObjectId;
   }

   public void setmObjectId(Integer mObjectId) {
       this.mObjectId = mObjectId;
   }

   public Integer getmObjectType() {
       return mObjectType;
   }

   public void setmObjectType(Integer mObjectType) {
       this.mObjectType = mObjectType;
   }

   public String getmPropertyHash() {
       return mPropertyHash;
   }

   public void setmPropertyHash(String mPropertyHash) {
       this.mPropertyHash = mPropertyHash;
   }

   public String getmPropertyLogHash() {
       return mPropertyLogHash;
   }

   public void setmPropertyLogHash(String mPropertyLogHash) {
       this.mPropertyLogHash = mPropertyLogHash;
   }
}
