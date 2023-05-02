// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: PostService.proto

package com.examples.lib;

/**
 * Protobuf type {@code com.example.LoadGroupPostsResponse}
 */
public final class LoadGroupPostsResponse extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:com.example.LoadGroupPostsResponse)
    LoadGroupPostsResponseOrBuilder {
private static final long serialVersionUID = 0L;
  // Use LoadGroupPostsResponse.newBuilder() to construct.
  private LoadGroupPostsResponse(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private LoadGroupPostsResponse() {
    posts_ = java.util.Collections.emptyList();
  }

  @java.lang.Override
  @SuppressWarnings({"unused"})
  protected java.lang.Object newInstance(
      UnusedPrivateParameter unused) {
    return new LoadGroupPostsResponse();
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return this.unknownFields;
  }
  private LoadGroupPostsResponse(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    this();
    if (extensionRegistry == null) {
      throw new java.lang.NullPointerException();
    }
    int mutable_bitField0_ = 0;
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
            if (!((mutable_bitField0_ & 0x00000001) != 0)) {
              posts_ = new java.util.ArrayList<com.examples.lib.PostProto>();
              mutable_bitField0_ |= 0x00000001;
            }
            posts_.add(
                input.readMessage(com.examples.lib.PostProto.parser(), extensionRegistry));
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
      if (((mutable_bitField0_ & 0x00000001) != 0)) {
        posts_ = java.util.Collections.unmodifiableList(posts_);
      }
      this.unknownFields = unknownFields.build();
      makeExtensionsImmutable();
    }
  }
  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return com.examples.lib.PostServiceProto.internal_static_com_example_LoadGroupPostsResponse_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return com.examples.lib.PostServiceProto.internal_static_com_example_LoadGroupPostsResponse_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            com.examples.lib.LoadGroupPostsResponse.class, com.examples.lib.LoadGroupPostsResponse.Builder.class);
  }

  public static final int POSTS_FIELD_NUMBER = 1;
  private java.util.List<com.examples.lib.PostProto> posts_;
  /**
   * <code>repeated .com.example.PostProto posts = 1;</code>
   */
  @java.lang.Override
  public java.util.List<com.examples.lib.PostProto> getPostsList() {
    return posts_;
  }
  /**
   * <code>repeated .com.example.PostProto posts = 1;</code>
   */
  @java.lang.Override
  public java.util.List<? extends com.examples.lib.PostProtoOrBuilder> 
      getPostsOrBuilderList() {
    return posts_;
  }
  /**
   * <code>repeated .com.example.PostProto posts = 1;</code>
   */
  @java.lang.Override
  public int getPostsCount() {
    return posts_.size();
  }
  /**
   * <code>repeated .com.example.PostProto posts = 1;</code>
   */
  @java.lang.Override
  public com.examples.lib.PostProto getPosts(int index) {
    return posts_.get(index);
  }
  /**
   * <code>repeated .com.example.PostProto posts = 1;</code>
   */
  @java.lang.Override
  public com.examples.lib.PostProtoOrBuilder getPostsOrBuilder(
      int index) {
    return posts_.get(index);
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
    for (int i = 0; i < posts_.size(); i++) {
      output.writeMessage(1, posts_.get(i));
    }
    unknownFields.writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    for (int i = 0; i < posts_.size(); i++) {
      size += com.google.protobuf.CodedOutputStream
        .computeMessageSize(1, posts_.get(i));
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
    if (!(obj instanceof com.examples.lib.LoadGroupPostsResponse)) {
      return super.equals(obj);
    }
    com.examples.lib.LoadGroupPostsResponse other = (com.examples.lib.LoadGroupPostsResponse) obj;

    if (!getPostsList()
        .equals(other.getPostsList())) return false;
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
    if (getPostsCount() > 0) {
      hash = (37 * hash) + POSTS_FIELD_NUMBER;
      hash = (53 * hash) + getPostsList().hashCode();
    }
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static com.examples.lib.LoadGroupPostsResponse parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.examples.lib.LoadGroupPostsResponse parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.examples.lib.LoadGroupPostsResponse parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.examples.lib.LoadGroupPostsResponse parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.examples.lib.LoadGroupPostsResponse parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.examples.lib.LoadGroupPostsResponse parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.examples.lib.LoadGroupPostsResponse parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static com.examples.lib.LoadGroupPostsResponse parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static com.examples.lib.LoadGroupPostsResponse parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static com.examples.lib.LoadGroupPostsResponse parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static com.examples.lib.LoadGroupPostsResponse parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static com.examples.lib.LoadGroupPostsResponse parseFrom(
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
  public static Builder newBuilder(com.examples.lib.LoadGroupPostsResponse prototype) {
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
   * Protobuf type {@code com.example.LoadGroupPostsResponse}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:com.example.LoadGroupPostsResponse)
      com.examples.lib.LoadGroupPostsResponseOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return com.examples.lib.PostServiceProto.internal_static_com_example_LoadGroupPostsResponse_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.examples.lib.PostServiceProto.internal_static_com_example_LoadGroupPostsResponse_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.examples.lib.LoadGroupPostsResponse.class, com.examples.lib.LoadGroupPostsResponse.Builder.class);
    }

    // Construct using com.examples.lib.LoadGroupPostsResponse.newBuilder()
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
        getPostsFieldBuilder();
      }
    }
    @java.lang.Override
    public Builder clear() {
      super.clear();
      if (postsBuilder_ == null) {
        posts_ = java.util.Collections.emptyList();
        bitField0_ = (bitField0_ & ~0x00000001);
      } else {
        postsBuilder_.clear();
      }
      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return com.examples.lib.PostServiceProto.internal_static_com_example_LoadGroupPostsResponse_descriptor;
    }

    @java.lang.Override
    public com.examples.lib.LoadGroupPostsResponse getDefaultInstanceForType() {
      return com.examples.lib.LoadGroupPostsResponse.getDefaultInstance();
    }

    @java.lang.Override
    public com.examples.lib.LoadGroupPostsResponse build() {
      com.examples.lib.LoadGroupPostsResponse result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public com.examples.lib.LoadGroupPostsResponse buildPartial() {
      com.examples.lib.LoadGroupPostsResponse result = new com.examples.lib.LoadGroupPostsResponse(this);
      int from_bitField0_ = bitField0_;
      if (postsBuilder_ == null) {
        if (((bitField0_ & 0x00000001) != 0)) {
          posts_ = java.util.Collections.unmodifiableList(posts_);
          bitField0_ = (bitField0_ & ~0x00000001);
        }
        result.posts_ = posts_;
      } else {
        result.posts_ = postsBuilder_.build();
      }
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
      if (other instanceof com.examples.lib.LoadGroupPostsResponse) {
        return mergeFrom((com.examples.lib.LoadGroupPostsResponse)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(com.examples.lib.LoadGroupPostsResponse other) {
      if (other == com.examples.lib.LoadGroupPostsResponse.getDefaultInstance()) return this;
      if (postsBuilder_ == null) {
        if (!other.posts_.isEmpty()) {
          if (posts_.isEmpty()) {
            posts_ = other.posts_;
            bitField0_ = (bitField0_ & ~0x00000001);
          } else {
            ensurePostsIsMutable();
            posts_.addAll(other.posts_);
          }
          onChanged();
        }
      } else {
        if (!other.posts_.isEmpty()) {
          if (postsBuilder_.isEmpty()) {
            postsBuilder_.dispose();
            postsBuilder_ = null;
            posts_ = other.posts_;
            bitField0_ = (bitField0_ & ~0x00000001);
            postsBuilder_ = 
              com.google.protobuf.GeneratedMessageV3.alwaysUseFieldBuilders ?
                 getPostsFieldBuilder() : null;
          } else {
            postsBuilder_.addAllMessages(other.posts_);
          }
        }
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
      com.examples.lib.LoadGroupPostsResponse parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage = (com.examples.lib.LoadGroupPostsResponse) e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }
    private int bitField0_;

    private java.util.List<com.examples.lib.PostProto> posts_ =
      java.util.Collections.emptyList();
    private void ensurePostsIsMutable() {
      if (!((bitField0_ & 0x00000001) != 0)) {
        posts_ = new java.util.ArrayList<com.examples.lib.PostProto>(posts_);
        bitField0_ |= 0x00000001;
       }
    }

    private com.google.protobuf.RepeatedFieldBuilderV3<
        com.examples.lib.PostProto, com.examples.lib.PostProto.Builder, com.examples.lib.PostProtoOrBuilder> postsBuilder_;

    /**
     * <code>repeated .com.example.PostProto posts = 1;</code>
     */
    public java.util.List<com.examples.lib.PostProto> getPostsList() {
      if (postsBuilder_ == null) {
        return java.util.Collections.unmodifiableList(posts_);
      } else {
        return postsBuilder_.getMessageList();
      }
    }
    /**
     * <code>repeated .com.example.PostProto posts = 1;</code>
     */
    public int getPostsCount() {
      if (postsBuilder_ == null) {
        return posts_.size();
      } else {
        return postsBuilder_.getCount();
      }
    }
    /**
     * <code>repeated .com.example.PostProto posts = 1;</code>
     */
    public com.examples.lib.PostProto getPosts(int index) {
      if (postsBuilder_ == null) {
        return posts_.get(index);
      } else {
        return postsBuilder_.getMessage(index);
      }
    }
    /**
     * <code>repeated .com.example.PostProto posts = 1;</code>
     */
    public Builder setPosts(
        int index, com.examples.lib.PostProto value) {
      if (postsBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensurePostsIsMutable();
        posts_.set(index, value);
        onChanged();
      } else {
        postsBuilder_.setMessage(index, value);
      }
      return this;
    }
    /**
     * <code>repeated .com.example.PostProto posts = 1;</code>
     */
    public Builder setPosts(
        int index, com.examples.lib.PostProto.Builder builderForValue) {
      if (postsBuilder_ == null) {
        ensurePostsIsMutable();
        posts_.set(index, builderForValue.build());
        onChanged();
      } else {
        postsBuilder_.setMessage(index, builderForValue.build());
      }
      return this;
    }
    /**
     * <code>repeated .com.example.PostProto posts = 1;</code>
     */
    public Builder addPosts(com.examples.lib.PostProto value) {
      if (postsBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensurePostsIsMutable();
        posts_.add(value);
        onChanged();
      } else {
        postsBuilder_.addMessage(value);
      }
      return this;
    }
    /**
     * <code>repeated .com.example.PostProto posts = 1;</code>
     */
    public Builder addPosts(
        int index, com.examples.lib.PostProto value) {
      if (postsBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensurePostsIsMutable();
        posts_.add(index, value);
        onChanged();
      } else {
        postsBuilder_.addMessage(index, value);
      }
      return this;
    }
    /**
     * <code>repeated .com.example.PostProto posts = 1;</code>
     */
    public Builder addPosts(
        com.examples.lib.PostProto.Builder builderForValue) {
      if (postsBuilder_ == null) {
        ensurePostsIsMutable();
        posts_.add(builderForValue.build());
        onChanged();
      } else {
        postsBuilder_.addMessage(builderForValue.build());
      }
      return this;
    }
    /**
     * <code>repeated .com.example.PostProto posts = 1;</code>
     */
    public Builder addPosts(
        int index, com.examples.lib.PostProto.Builder builderForValue) {
      if (postsBuilder_ == null) {
        ensurePostsIsMutable();
        posts_.add(index, builderForValue.build());
        onChanged();
      } else {
        postsBuilder_.addMessage(index, builderForValue.build());
      }
      return this;
    }
    /**
     * <code>repeated .com.example.PostProto posts = 1;</code>
     */
    public Builder addAllPosts(
        java.lang.Iterable<? extends com.examples.lib.PostProto> values) {
      if (postsBuilder_ == null) {
        ensurePostsIsMutable();
        com.google.protobuf.AbstractMessageLite.Builder.addAll(
            values, posts_);
        onChanged();
      } else {
        postsBuilder_.addAllMessages(values);
      }
      return this;
    }
    /**
     * <code>repeated .com.example.PostProto posts = 1;</code>
     */
    public Builder clearPosts() {
      if (postsBuilder_ == null) {
        posts_ = java.util.Collections.emptyList();
        bitField0_ = (bitField0_ & ~0x00000001);
        onChanged();
      } else {
        postsBuilder_.clear();
      }
      return this;
    }
    /**
     * <code>repeated .com.example.PostProto posts = 1;</code>
     */
    public Builder removePosts(int index) {
      if (postsBuilder_ == null) {
        ensurePostsIsMutable();
        posts_.remove(index);
        onChanged();
      } else {
        postsBuilder_.remove(index);
      }
      return this;
    }
    /**
     * <code>repeated .com.example.PostProto posts = 1;</code>
     */
    public com.examples.lib.PostProto.Builder getPostsBuilder(
        int index) {
      return getPostsFieldBuilder().getBuilder(index);
    }
    /**
     * <code>repeated .com.example.PostProto posts = 1;</code>
     */
    public com.examples.lib.PostProtoOrBuilder getPostsOrBuilder(
        int index) {
      if (postsBuilder_ == null) {
        return posts_.get(index);  } else {
        return postsBuilder_.getMessageOrBuilder(index);
      }
    }
    /**
     * <code>repeated .com.example.PostProto posts = 1;</code>
     */
    public java.util.List<? extends com.examples.lib.PostProtoOrBuilder> 
         getPostsOrBuilderList() {
      if (postsBuilder_ != null) {
        return postsBuilder_.getMessageOrBuilderList();
      } else {
        return java.util.Collections.unmodifiableList(posts_);
      }
    }
    /**
     * <code>repeated .com.example.PostProto posts = 1;</code>
     */
    public com.examples.lib.PostProto.Builder addPostsBuilder() {
      return getPostsFieldBuilder().addBuilder(
          com.examples.lib.PostProto.getDefaultInstance());
    }
    /**
     * <code>repeated .com.example.PostProto posts = 1;</code>
     */
    public com.examples.lib.PostProto.Builder addPostsBuilder(
        int index) {
      return getPostsFieldBuilder().addBuilder(
          index, com.examples.lib.PostProto.getDefaultInstance());
    }
    /**
     * <code>repeated .com.example.PostProto posts = 1;</code>
     */
    public java.util.List<com.examples.lib.PostProto.Builder> 
         getPostsBuilderList() {
      return getPostsFieldBuilder().getBuilderList();
    }
    private com.google.protobuf.RepeatedFieldBuilderV3<
        com.examples.lib.PostProto, com.examples.lib.PostProto.Builder, com.examples.lib.PostProtoOrBuilder> 
        getPostsFieldBuilder() {
      if (postsBuilder_ == null) {
        postsBuilder_ = new com.google.protobuf.RepeatedFieldBuilderV3<
            com.examples.lib.PostProto, com.examples.lib.PostProto.Builder, com.examples.lib.PostProtoOrBuilder>(
                posts_,
                ((bitField0_ & 0x00000001) != 0),
                getParentForChildren(),
                isClean());
        posts_ = null;
      }
      return postsBuilder_;
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


    // @@protoc_insertion_point(builder_scope:com.example.LoadGroupPostsResponse)
  }

  // @@protoc_insertion_point(class_scope:com.example.LoadGroupPostsResponse)
  private static final com.examples.lib.LoadGroupPostsResponse DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new com.examples.lib.LoadGroupPostsResponse();
  }

  public static com.examples.lib.LoadGroupPostsResponse getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<LoadGroupPostsResponse>
      PARSER = new com.google.protobuf.AbstractParser<LoadGroupPostsResponse>() {
    @java.lang.Override
    public LoadGroupPostsResponse parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return new LoadGroupPostsResponse(input, extensionRegistry);
    }
  };

  public static com.google.protobuf.Parser<LoadGroupPostsResponse> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<LoadGroupPostsResponse> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public com.examples.lib.LoadGroupPostsResponse getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}
