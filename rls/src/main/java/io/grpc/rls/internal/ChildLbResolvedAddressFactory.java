/*
 * Copyright 2020 The gRPC Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.grpc.rls.internal;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import io.grpc.Attributes;
import io.grpc.EquivalentAddressGroup;
import io.grpc.LoadBalancer.ResolvedAddresses;
import java.util.Collections;
import java.util.List;

/** Factory to create {@link io.grpc.LoadBalancer.ResolvedAddresses} from child load-balancers. */
public final class ChildLbResolvedAddressFactory {

  private final List<EquivalentAddressGroup> addresses;
  private final Attributes attributes;

  /** Constructor. */
  public ChildLbResolvedAddressFactory(
      List<EquivalentAddressGroup> addresses, Attributes attributes) {
    checkArgument(addresses != null && !addresses.isEmpty(), "Address must be provided");
    this.addresses = Collections.unmodifiableList(addresses);
    this.attributes = checkNotNull(attributes, "attributes");
  }

  /** Creates {@link ResolvedAddresses} with given child lb config. */
  public ResolvedAddresses create(Object childLbConfig) {
    return ResolvedAddresses.newBuilder()
        .setAddresses(addresses)
        .setAttributes(attributes)
        .setLoadBalancingPolicyConfig(childLbConfig)
        .build();
  }
}
