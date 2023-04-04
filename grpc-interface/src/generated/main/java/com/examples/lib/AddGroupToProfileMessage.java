// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: ProfileService.proto

package com.examples.lib;

/**
 * Protobuf type {@code com.example.AddGroupToProfileMessage}
 */
public final class AddGroupToProfileMessage extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:com.example.AddGroupToProfileMessage)
    AddGroupToProfileMessageOrBuilder {
private static final long serialVersionUID = 0L;
  // Use AddGroupToProfileMessage.newBuilder() to construct.
  private AddGroupToProfileMessage(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private AddGroupToProfileMessage() {
    session_ = "";
    profileId_ = "";
    groupToAdd_ = "";
  }

  @java.lang.Override
  @SuppressWarnings({"unused"})
  protected java.lang.Object newInstance(
      UnusedPrivateParameter unused) {
    return new AddGroupToProfileMessage();
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return this.unknownFields;
  }
  private AddGroupToProfileMessage(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    this();
    if (extensionRegistry == null) {
      throw new java.lang.NullPointerException();
    }
    com.google.protobuf.UnknownFieldSet.Builder unknownFields =
        com.google.protobuf.UnknownFieldSet.newBuilder();
    try {
      boolean done = false;
      while (!done) {
        int tag = input.readTag();
        switch (tag) {
          case 0:
            done = true;
            break;
          case 10: {
            java.lang.String s = input.readStringRequireUtf8();

            session_ = s;
            break;
          }
          case 18: {
            java.lang.String s = input.readStringRequireUtf8();

            profileId_ = s;
            break;
          }
          case 26: {
            java.lang.String s = input.readStringRequireUtf8();

            groupToAdd_ = s;
            break;
          }
          default: {
            if (!parseUnknownField(
                input, unknownFields, extensionRegistry, tag)) {
              done = true;
            }
            break;
          }
        }
      }
    } catch (com.google.protobuf.InvalidProtocolBufferException e) {
      throw e.setUnfinishedMessage(this);
    } catch (java.io.IOException e) {
      throw new com.google.protobuf.InvalidProtocolBufferException(
          e).setUnfinishedMessage(this);
    } finally {
      this.unknownFields = unknownFields.build();
      makeExtensionsImmutable();
    }
  }
  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return com.examples.lib.ProfileServiceProto.internal_static_com_example_AddGroupToProfileMessage_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return com.examples.lib.ProfileServiceProto.internal_static_com_example_AddGroupToProfileMessage_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            com.examples.lib.AddGroupToProfileMessage.class, com.examples.lib.AddGroupToProfileMessage.Builder.class);
  }

  public static final int SESSION_FIELD_NUMBER = 1;
  private volatile java.lang.Object session_;
  /**
   * <code>string session = 1;</code>
   * @return The session.
   */
  @java.lang.Override
  public java.lang.String getSession() {
    java.lang.Object ref = session_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = 
          (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      session_ = s;
      return s;
    }
  }
  /**
   * <code>string session = 1;</code>
   * @return The bytes for session.
   */
  @java.lang.Override
  public com.google.protobuf.ByteString
      getSessionBytes() {
    java.lang.Object ref = session_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b = 
          com.google.protobuf.ByteString.copyFromUtf8(
              (java.lang.String) ref);
      session_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  public static final int PROFILEID_FIELD_NUMBER = 2;
  private volatile java.lang.Object profileId_;
  /**
   * <code>string profileId = 2;</code>
   * @return The profileId.
   */
  @java.lang.Override
  public java.lang.String getProfileId() {
    java.lang.Object ref = profileId_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = 
          (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      profileId_ = s;
      return s;
    }
  }
  /**
   * <code>string profileId = 2;</code>
   * @return The bytes for profileId.
   */
  @java.lang.Override
  public com.google.protobuf.ByteString
      getProfileIdBytes() {
    java.lang.Object ref = profileId_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b = 
          com.google.protobuf.ByteString.copyFromUtf8(
              (java.lang.String) ref);
      profileId_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  public static final int GROUPTOADD_FIELD_NUMBER = 3;
  private volatile java.lang.Object groupToAdd_;
  /**
   * <code>string groupToAdd = 3;</code>
   * @return The groupToAdd.
   */
  @java.lang.Override
  public java.lang.String getGroupToAdd() {
    java.lang.Object ref = groupToAdd_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = 
          (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      groupToAdd_ = s;
      return s;
    }
  }
  /**
   * <code>string groupToAdd = 3;</code>
   * @return The bytes for groupToAdd.
   */
  @java.lang.Override
  public com.google.protobuf.ByteString
      getGroupToAddBytes() {
    java.lang.Object ref = groupToAdd_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b = 
          com.google.protobuf.ByteString.copyFromUtf8(
              (java.lang.String) ref);
      groupToAdd_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  private byte memoizedIsInitialized = -1;
  @java.lang.Override
  public final boolean isInitialized() {
    byte isInitialized = memoizedIsInitialized;
    if (isInitialized == 1) return true;
    if (isInitialized == 0) return false;

    memoizedIsInitialized = 1;
    return true;
  }

  @java.lang.Override
  public void writeTo(com.google.protobuf.CodedOutputStream output)
                      throws java.io.IOException {
    if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(session_)) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 1, session_);
    }
    if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(profileId_)) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 2, profileId_);
    }
    if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(groupToAdd_)) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 3, groupToAdd_);
    }
    unknownFields.writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(session_)) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(1, session_);
    }
    if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(profileId_)) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(2, profileId_);
    }
    if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(groupToAdd_)) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(3, groupToAdd_);
    }
    size += unknownFields.getSerializedSize();
    memoizedSize = size;
    return size;
  }

  @java.lang.Override
  public boolean equals(final java.lang.Object obj) {
    if (obj == this) {
     return true;
    }
    if (!(obj instanceof com.examples.lib.AddGroupToProfileMessage)) {
      return super.equals(obj);
    }
    com.examples.lib.AddGroupToProfileMessage other = (com.examples.lib.AddGroupToProfileMessage) obj;

    if (!getSession()
        .equals(other.getSession())) return false;
    if (!getProfileId()
        .equals(other.getProfileId())) return false;
    if (!getGroupToAdd()
        .equals(other.getGroupToAdd())) return false;
    if (!unknownFields.equals(other.unknownFields)) return false;
    return true;
  }

  @java.lang.Override
  public int hashCode() {
    if (memoizedHashCode != 0) {
      return memoizedHashCode;
    }
    int hash = 41;
    hash = (19 * hash) + getDescriptor().hashCode();
    hash = (37 * hash) + SESSION_FIELD_NUMBER;
    hash = (53 * hash) + getSession().hashCode();
    hash = (37 * hash) + PROFILEID_FIELD_NUMBER;
    hash = (53 * hash) + getProfileId().hashCode();
    hash = (37 * hash) + GROUPTOADD_FIELD_NUMBER;
    hash = (53 * hash) + getGroupToAdd().hashCode();
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static com.examples.lib.AddGroupToProfileMessage parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.examples.lib.AddGroupToProfileMessage parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.examples.lib.AddGroupToProfileMessage parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.examples.lib.AddGroupToProfileMessage parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.examples.lib.AddGroupToProfileMessage parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.examples.lib.AddGroupToProfileMessage parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.examples.lib.AddGroupToProfileMessage parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static com.examples.lib.AddGroupToProfileMessage parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static com.examples.lib.AddGroupToProfileMessage parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static com.examples.lib.AddGroupToProfileMessage parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static com.examples.lib.AddGroupToProfileMessage parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static com.examples.lib.AddGroupToProfileMessage parseFrom(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }

  @java.lang.Override
  public Builder newBuilderForType() { return newBuilder(); }
  public static Builder newBuilder() {
    return DEFAULT_INSTANCE.toBuilder();
  }
  public static Builder newBuilder(com.examples.lib.AddGroupToProfileMessage prototype) {
    return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
  }
  @java.lang.Override
  public Builder toBuilder() {
    return this == DEFAULT_INSTANCE
        ? new Builder() : new Builder().mergeFrom(this);
  }

  @java.lang.Override
  protected Builder newBuilderForType(
      com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
    Builder builder = new Builder(parent);
    return builder;
  }
  /**
   * Protobuf type {@code com.example.AddGroupToProfileMessage}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:com.example.AddGroupToProfileMessage)
      com.examples.lib.AddGroupToProfileMessageOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return com.examples.lib.ProfileServiceProto.internal_static_com_example_AddGroupToProfileMessage_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.examples.lib.ProfileServiceProto.internal_static_com_example_AddGroupToProfileMessage_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.examples.lib.AddGroupToProfileMessage.class, com.examples.lib.AddGroupToProfileMessage.Builder.class);
    }

    // Construct using com.examples.lib.AddGroupToProfileMessage.newBuilder()
    private Builder() {
      maybeForceBuilderInitialization();
    }

    private Builder(
        com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      super(parent);
      maybeForceBuilderInitialization();
    }
    private void maybeForceBuilderInitialization() {
      if (com.google.protobuf.GeneratedMessageV3
              .alwaysUseFieldBuilders) {
      }
    }
    @java.lang.Override
    public Builder clear() {
      super.clear();
      session_ = "";

      profileId_ = "";

      groupToAdd_ = "";

      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return com.examples.lib.ProfileServiceProto.internal_static_com_example_AddGroupToProfileMessage_descriptor;
    }

    @java.lang.Override
    public com.examples.lib.AddGroupToProfileMessage getDefaultInstanceForType() {
      return com.examples.lib.AddGroupToProfileMessage.getDefaultInstance();
    }

    @java.lang.Override
    public com.examples.lib.AddGroupToProfileMessage build() {
      com.examples.lib.AddGroupToProfileMessage result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public com.examples.lib.AddGroupToProfileMessage buildPartial() {
      com.examples.lib.AddGroupToProfileMessage result = new com.examples.lib.AddGroupToProfileMessage(this);
      result.session_ = session_;
      result.profileId_ = profileId_;
      result.groupToAdd_ = groupToAdd_;
      onBuilt();
      return result;
    }

    @java.lang.Override
    public Builder clone() {
      return super.clone();
    }
    @java.lang.Override
    public Builder setField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        java.lang.Object value) {
      return super.setField(field, value);
    }
    @java.lang.Override
    public Builder clearField(
        com.google.protobuf.Descriptors.FieldDescriptor field) {
      return super.clearField(field);
    }
    @java.lang.Override
    public Builder clearOneof(
        com.google.protobuf.Descriptors.OneofDescriptor oneof) {
      return super.clearOneof(oneof);
    }
    @java.lang.Override
    public Builder setRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        int index, java.lang.Object value) {
      return super.setRepeatedField(field, index, value);
    }
    @java.lang.Override
    public Builder addRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        java.lang.Object value) {
      return super.addRepeatedField(field, value);
    }
    @java.lang.Override
    public Builder mergeFrom(com.google.protobuf.Message other) {
      if (other instanceof com.examples.lib.AddGroupToProfileMessage) {
        return mergeFrom((com.examples.lib.AddGroupToProfileMessage)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(com.examples.lib.AddGroupToProfileMessage other) {
      if (other == com.examples.lib.AddGroupToProfileMessage.getDefaultInstance()) return this;
      if (!other.getSession().isEmpty()) {
        session_ = other.session_;
        onChanged();
      }
      if (!other.getProfileId().isEmpty()) {
        profileId_ = other.profileId_;
        onChanged();
      }
      if (!other.getGroupToAdd().isEmpty()) {
        groupToAdd_ = other.groupToAdd_;
        onChanged();
      }
      this.mergeUnknownFields(other.unknownFields);
      onChanged();
      return this;
    }

    @java.lang.Override
    public final boolean isInitialized() {
      return true;
    }

    @java.lang.Override
    public Builder mergeFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      com.examples.lib.AddGroupToProfileMessage parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage = (com.examples.lib.AddGroupToProfileMessage) e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }

    private java.lang.Object session_ = "";
    /**
     * <code>string session = 1;</code>
     * @return The session.
     */
    public java.lang.String getSession() {
      java.lang.Object ref = session_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        session_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     * <code>string session = 1;</code>
     * @return The bytes for session.
     */
    public com.google.protobuf.ByteString
        getSessionBytes() {
      java.lang.Object ref = session_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        session_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <code>string session = 1;</code>
     * @param value The session to set.
     * @return This builder for chaining.
     */
    public Builder setSession(
        java.lang.String value) {
      if (value == null) {
    throw new NullPointerException();
  }
  
      session_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>string session = 1;</code>
     * @return This builder for chaining.
     */
    public Builder clearSession() {
      
      session_ = getDefaultInstance().getSession();
      onChanged();
      return this;
    }
    /**
     * <code>string session = 1;</code>
     * @param value The bytes for session to set.
     * @return This builder for chaining.
     */
    public Builder setSessionBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
      
      session_ = value;
      onChanged();
      return this;
    }

    private java.lang.Object profileId_ = "";
    /**
     * <code>string profileId = 2;</code>
     * @return The profileId.
     */
    public java.lang.String getProfileId() {
      java.lang.Object ref = profileId_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        profileId_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     * <code>string profileId = 2;</code>
     * @return The bytes for profileId.
     */
    public com.google.protobuf.ByteString
        getProfileIdBytes() {
      java.lang.Object ref = profileId_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        profileId_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <code>string profileId = 2;</code>
     * @param value The profileId to set.
     * @return This builder for chaining.
     */
    public Builder setProfileId(
        java.lang.String value) {
      if (value == null) {
    throw new NullPointerException();
  }
  
      profileId_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>string profileId = 2;</code>
     * @return This builder for chaining.
     */
    public Builder clearProfileId() {
      
      profileId_ = getDefaultInstance().getProfileId();
      onChanged();
      return this;
    }
    /**
     * <code>string profileId = 2;</code>
     * @param value The bytes for profileId to set.
     * @return This builder for chaining.
     */
    public Builder setProfileIdBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
      
      profileId_ = value;
      onChanged();
      return this;
    }

    private java.lang.Object groupToAdd_ = "";
    /**
     * <code>string groupToAdd = 3;</code>
     * @return The groupToAdd.
     */
    public java.lang.String getGroupToAdd() {
      java.lang.Object ref = groupToAdd_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        groupToAdd_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     * <code>string groupToAdd = 3;</code>
     * @return The bytes for groupToAdd.
     */
    public com.google.protobuf.ByteString
        getGroupToAddBytes() {
      java.lang.Object ref = groupToAdd_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        groupToAdd_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <code>string groupToAdd = 3;</code>
     * @param value The groupToAdd to set.
     * @return This builder for chaining.
     */
    public Builder setGroupToAdd(
        java.lang.String value) {
      if (value == null) {
    throw new NullPointerException();
  }
  
      groupToAdd_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>string groupToAdd = 3;</code>
     * @return This builder for chaining.
     */
    public Builder clearGroupToAdd() {
      
      groupToAdd_ = getDefaultInstance().getGroupToAdd();
      onChanged();
      return this;
    }
    /**
     * <code>string groupToAdd = 3;</code>
     * @param value The bytes for groupToAdd to set.
     * @return This builder for chaining.
     */
    public Builder setGroupToAddBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
      
      groupToAdd_ = value;
      onChanged();
      return this;
    }
    @java.lang.Override
    public final Builder setUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.setUnknownFields(unknownFields);
    }

    @java.lang.Override
    public final Builder mergeUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.mergeUnknownFields(unknownFields);
    }


    // @@protoc_insertion_point(builder_scope:com.example.AddGroupToProfileMessage)
  }

  // @@protoc_insertion_point(class_scope:com.example.AddGroupToProfileMessage)
  private static final com.examples.lib.AddGroupToProfileMessage DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new com.examples.lib.AddGroupToProfileMessage();
  }

  public static com.examples.lib.AddGroupToProfileMessage getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<AddGroupToProfileMessage>
      PARSER = new com.google.protobuf.AbstractParser<AddGroupToProfileMessage>() {
    @java.lang.Override
    public AddGroupToProfileMessage parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return new AddGroupToProfileMessage(input, extensionRegistry);
    }
  };

  public static com.google.protobuf.Parser<AddGroupToProfileMessage> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<AddGroupToProfileMessage> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public com.examples.lib.AddGroupToProfileMessage getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

